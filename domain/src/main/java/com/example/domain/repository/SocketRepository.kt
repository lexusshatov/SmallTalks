package com.example.domain.repository

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.remote_protocol.BaseDto.Action.*
import com.example.domain.repository.base.repository.RemoteData
import com.example.domain.repository.remote.ConnectState
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket

class SocketRepository(
    private val gson: Gson
) : RemoteData {
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    private var timeoutPong = 15000L

    private val job = SupervisorJob()
    private val backgroundScope = CoroutineScope(Dispatchers.IO) + job

    override lateinit var me: com.example.domain.remote_protocol.User

    private val mutableMessages = MutableSharedFlow<com.example.domain.remote_protocol.MessageDto>()
    override val messages: SharedFlow<com.example.domain.remote_protocol.MessageDto>
        get() = mutableMessages

    private val mutableUsers = MutableLiveData<List<com.example.domain.remote_protocol.User>>()
    override val users: LiveData<List<com.example.domain.remote_protocol.User>>
        get() = mutableUsers

    private val mutableConnectState = MutableStateFlow<ConnectState>(ConnectState.Nothing)
    override val connectState: StateFlow<ConnectState>
        get() = mutableConnectState

    override suspend fun connect(userName: String) {
        runCatching {
            mutableConnectState.value = ConnectState.Connect(userName)

            val ip = getServerAddress(TIMEOUT_BROADCAST)

            //init socket, in/out streams
            connectToServer(ip, TIMEOUT_CONNECT_SERVER)
            val userId = getUserIdAsync(socket, input, TIMEOUT_GET_UID)
            me = com.example.domain.remote_protocol.User(userId, userName)
            connectUser(output, me.id, userName)
            getUsers(me.id)

            startPings(output, userId)
            startListening(input)
        }
            .onSuccess { mutableConnectState.value = ConnectState.Success }
            .onFailure {
                freeingResources()
            }
    }

    private fun startListening(input: BufferedReader) {
        backgroundScope.launch(Dispatchers.IO) {
            while (timeoutPong > 0 && isActive) {
                val delayTime = 1000L
                delay(delayTime)
                timeoutPong -= delayTime
            }
            reconnect(me.name)
        }
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                while (isActive) {
                    val baseDto =
                        gson.fromJson(runInterruptible { input.readLine() }, com.example.domain.remote_protocol.BaseDto::class.java)
                    when (baseDto.action) {
                        NEW_MESSAGE -> {
                            val newMessageDto =
                                gson.fromJson(baseDto.payload, com.example.domain.remote_protocol.MessageDto::class.java)
                            mutableMessages.emit(newMessageDto)
                        }
                        USERS_RECEIVED -> {
                            val usersReceivedDto =
                                gson.fromJson(baseDto.payload, com.example.domain.remote_protocol.UsersReceivedDto::class.java)
                            mutableUsers.postValue(usersReceivedDto.users)
                        }
                        PONG -> {
                            timeoutPong = 15000L
                        }
                        DISCONNECT -> {
                            reconnect(me.name)
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private suspend fun reconnect(userName: String) {
        disconnect()
        connect(userName)
    }

    override suspend fun sendMessage(to: com.example.domain.remote_protocol.User, message: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val sendMessageDto =
                com.example.domain.remote_protocol.SendMessageDto(me.id, to.id, message)
            val baseDto = com.example.domain.remote_protocol.BaseDto(
                SEND_MESSAGE,
                gson.toJson(sendMessageDto)
            )
            output.println(gson.toJson(baseDto))
        }
    }

    private suspend fun getServerAddress(timeout: Int) =
        runInterruptible(backgroundScope.coroutineContext) {
            runCatching {
                if (isEmulator()) EMULATOR_IP
                else {
                    val buffer = ByteArray(100)
                    val packet = DatagramPacket(
                        buffer,
                        buffer.size,
                        InetAddress.getByName(HOST_BROADCAST),
                        PORT_BROADCAST
                    )

                    var message = ""
                    while (backgroundScope.isActive && message.isEmpty()) {
                        DatagramSocket().apply {
                            soTimeout = timeout
                            send(packet)
                            receive(packet)
                            message = String(packet.data).trim { it.code == 0 }
                            close()
                        }
                    }

                    val udpDto = gson.fromJson(message, com.example.domain.remote_protocol.UdpDto::class.java)
                    udpDto.ip
                }
            }
                .onSuccess { ip -> Log.d(TAG, "Server IP: $ip") }
                .onFailure { error("Failed to get server address", it) }
                .getOrThrow()
        }

    private suspend fun connectToServer(ip: String, timeout: Int) =
        runInterruptible(backgroundScope.coroutineContext) {
            runCatching {
                Socket(ip, PORT_SERVER).apply {
                    soTimeout = timeout
                    socket = this
                    input = BufferedReader(InputStreamReader(getInputStream()))
                    output = PrintWriter(OutputStreamWriter(getOutputStream()))
                }
            }
                .onSuccess { Log.d(TAG, "Connected") }
                .onFailure { error("Connect to server failed", it) }
        }

    private suspend fun getUserIdAsync(socket: Socket, input: BufferedReader, timeout: Int) =
        runInterruptible(backgroundScope.coroutineContext) {
            runCatching {
                socket.soTimeout = timeout
                val inputUserId = input.readLine()
                socket.soTimeout = TIMEOUT_INFINITY
                val baseDto = gson.fromJson(inputUserId, com.example.domain.remote_protocol.BaseDto::class.java)
                val connectedDto = gson.fromJson(baseDto.payload, com.example.domain.remote_protocol.ConnectedDto::class.java)
                connectedDto.id
            }
                .onSuccess { userId -> Log.d(TAG, "User ID: $userId") }
                .onFailure { error("Failed to get user id", it) }
                .getOrThrow()
        }

    private fun connectUser(output: PrintWriter, userId: String, userName: String) {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                val connectDto = com.example.domain.remote_protocol.ConnectDto(userId, userName)
                val baseDto =
                    com.example.domain.remote_protocol.BaseDto(CONNECT, gson.toJson(connectDto))
                output.println(gson.toJson(baseDto))
            }
                .onSuccess { Log.d(TAG, "Sent user connect") }
                .onFailure { error("User connecting failed", it) }
        }
    }

    private fun startPings(output: PrintWriter, userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val baseDto = com.example.domain.remote_protocol.BaseDto(
                PING,
                gson.toJson(com.example.domain.remote_protocol.PingDto(userId))
            )
            val pingMessage = gson.toJson(baseDto)
            while (isActive) {
                output.println(pingMessage)
                output.flush()
                delay(PING_DELAY)
            }
        }
    }

    private fun getUsers(userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            while (isActive) {
                val baseDto = com.example.domain.remote_protocol.BaseDto(
                    GET_USERS,
                    gson.toJson(com.example.domain.remote_protocol.GetUsersDto(userId))
                )
                val getUsersMessage = gson.toJson(baseDto)
                output.println(getUsersMessage)
                delay(USERS_REQUEST_DELAY)
            }
        }
    }

    private fun freeingResources() {
        runCatching {
            input.close()
            output.close()
            socket.close()
        }
    }

    override suspend fun disconnect() {
        withContext(NonCancellable) {
            freeingResources()
            backgroundScope.cancel()
        }
    }

    private fun error(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
        mutableConnectState.value = ConnectState.Error(message)
    }

    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }


    private companion object {
        val TAG: String = SocketRepository::class.java.simpleName
        const val EMULATOR_IP = "10.0.2.2"
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666

        const val TIMEOUT_BROADCAST = 15000
        const val TIMEOUT_CONNECT_SERVER = 7000
        const val TIMEOUT_GET_UID = 5000

        const val PING_DELAY = 5000L
        const val USERS_REQUEST_DELAY = 4000L

        const val TIMEOUT_INFINITY = 0
    }
}