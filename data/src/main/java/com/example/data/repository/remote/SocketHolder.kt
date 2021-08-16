package com.example.data.repository.remote

import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.natife.example.domain.Message
import com.natife.example.domain.dto.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import javax.inject.Inject

class SocketHolder @Inject constructor(private val gson: Gson) {

    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    private var timeoutPong = 15000L
    private val job = SupervisorJob()
    private val backgroundScope = CoroutineScope(Dispatchers.IO) + job

    lateinit var me: User
    val messages = MutableSharedFlow<Message>()
    val users = MutableSharedFlow<List<User>>()

    suspend fun connect(userName: String) {
        val ip = getServerAddress(TIMEOUT_BROADCAST)
        //init socket, in/out streams
        connectToServer(ip, TIMEOUT_CONNECT_SERVER)
        val userId = getUserId(TIMEOUT_GET_UID)
        me = User(userId, userName)
        connectUser(me.id, userName)
        getUsers(me.id)
        startPings(userId)
        startListening()
    }

    private fun startListening() {
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
                        gson.fromJson(runInterruptible { input.readLine() }, BaseDto::class.java)
                    when (baseDto.action) {
                        BaseDto.Action.NEW_MESSAGE -> {
                            val newMessageDto =
                                gson.fromJson(baseDto.payload, MessageDto::class.java)
                            val message = Message(
                                from = newMessageDto.from,
                                to = me,
                                message = newMessageDto.message
                            )
                            messages.emit(message)
                        }
                        BaseDto.Action.USERS_RECEIVED -> {
                            val usersReceivedDto =
                                gson.fromJson(baseDto.payload, UsersReceivedDto::class.java)
                            users.emit(usersReceivedDto.users)
                        }
                        BaseDto.Action.PONG -> {
                            timeoutPong = 15000L
                        }
                        BaseDto.Action.DISCONNECT -> {
                            reconnect(me.name)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private suspend fun reconnect(userName: String) {
        disconnect()
        connect(userName)
    }

    fun sendMessageToServer(message: String) {
        backgroundScope.launch(Dispatchers.IO) {
            output.println(message)
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

                    val udpDto = gson.fromJson(message, UdpDto::class.java)
                    udpDto.ip
                }
            }
                .onSuccess { ip -> Log.d(TAG, "Server IP: $ip") }
                .onFailure { throw IllegalStateException("Failed to get server address", it) }
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
                .onFailure { throw IllegalStateException("Connect to server failed", it) }
        }

    private suspend fun getUserId(timeout: Int) =
        runInterruptible(backgroundScope.coroutineContext) {
            runCatching {
                socket.soTimeout = timeout
                val inputUserId = input.readLine()
                socket.soTimeout = TIMEOUT_INFINITY
                val baseDto = gson.fromJson(inputUserId, BaseDto::class.java)
                val connectedDto = gson.fromJson(baseDto.payload, ConnectedDto::class.java)
                connectedDto.id
            }
                .onSuccess { userId -> Log.d(TAG, "User ID: $userId") }
                .onFailure { throw IllegalStateException("Failed to get user id", it) }
                .getOrThrow()
        }

    private fun connectUser(userId: String, userName: String) {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                val connectDto = ConnectDto(userId, userName)
                val baseDto = BaseDto(BaseDto.Action.CONNECT, gson.toJson(connectDto))
                output.println(gson.toJson(baseDto))
            }
                .onSuccess { Log.d(TAG, "Sent user connect") }
                .onFailure { throw IllegalStateException("User connecting failed", it) }
        }
    }

    private fun startPings(userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val baseDto = BaseDto(
                BaseDto.Action.PING,
                gson.toJson(PingDto(userId))
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
                val baseDto = BaseDto(
                    BaseDto.Action.GET_USERS,
                    gson.toJson(GetUsersDto(userId))
                )
                val getUsersMessage = gson.toJson(baseDto)
                output.println(getUsersMessage)
                delay(USERS_REQUEST_DELAY)
            }
        }
    }

    fun disconnect() {
        input.close()
        output.close()
        socket.close()
        job.cancelChildren()
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
        val TAG: String = SocketHolder::class.java.simpleName
        const val EMULATOR_IP = "10.0.2.2"
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666

        const val TIMEOUT_BROADCAST = 5000
        const val TIMEOUT_CONNECT_SERVER = 7000
        const val TIMEOUT_GET_UID = 5000

        const val PING_DELAY = 5000L
        const val USERS_REQUEST_DELAY = 4000L
        const val TIMEOUT_INFINITY = 0
    }
}
