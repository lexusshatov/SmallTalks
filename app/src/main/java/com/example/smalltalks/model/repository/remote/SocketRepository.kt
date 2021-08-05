package com.example.smalltalks.model.repository.remote

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smalltalks.model.remote_protocol.*
import com.example.smalltalks.model.remote_protocol.BaseDto.Action.*
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

    private val backgroundScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    override lateinit var me: User

    private val mutableMessages = MutableSharedFlow<MessageDto>()
    override val messages: SharedFlow<MessageDto>
        get() = mutableMessages

    private val mutableUsers by lazy {
        backgroundScope.launch(Dispatchers.IO) {
            while (true) {
                userRequest(output, me.id)
                delay(USERS_REQUEST_DELAY)
            }
        }
        MutableLiveData<List<User>>()
    }
    override val users: LiveData<List<User>>
        get() = mutableUsers

    private val mutableConnectState = MutableStateFlow<ConnectState>(ConnectState.Nothing)
    override val connectState: StateFlow<ConnectState>
        get() = mutableConnectState

    override fun connect(userName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            runCatching {
                mutableConnectState.value = ConnectState.Connect(userName)
                // FIXME: 30.07.2021
                val ip =
                    "192.168.88.26"//withTimeout(TIMEOUT_BROADCAST) { getServerAddressAsync().await() }
                //init socket, in/out streams
                withTimeout(TIMEOUT_CONNECT_SERVER) { connectToServer(ip).join() }
                val userId = withTimeout(TIMEOUT_GET_UID) { getUserIdAsync(input).await() }
                me = User(userId, userName)
                connectUser(output, userId, userName)
                mutableConnectState.value = ConnectState.Success

                startPings(output, userId)
                startListening(input)
            }
                .onFailure {
                    mutableConnectState.value = ConnectState.Error(it.message?: "Unknown error")
                    Log.e(TAG, it.toString())
                    freeingResources()
                }
        }
    }

    private fun startListening(input: BufferedReader) {
        backgroundScope.launch(Dispatchers.IO) {
            while (timeoutPong > 0) {
                val delayTime = 1000L
                delay(delayTime)
                timeoutPong -= delayTime
            }
            reconnect(me.name)
        }
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                while (true) {
                    val baseDto = gson.fromJson(input.readLine(), BaseDto::class.java)
                    when (baseDto.action) {
                        NEW_MESSAGE -> {
                            val newMessageDto =
                                gson.fromJson(baseDto.payload, MessageDto::class.java)
                            mutableMessages.emit(newMessageDto)
                        }
                        USERS_RECEIVED -> {
                            val usersReceivedDto =
                                gson.fromJson(baseDto.payload, UsersReceivedDto::class.java)
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

    private fun reconnect(userName: String) {
        disconnect()
        connect(userName)
    }

    override fun sendMessage(to: String, message: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val sendMessageDto = SendMessageDto(me.id, to, message)
            val baseDto = BaseDto(SEND_MESSAGE, gson.toJson(sendMessageDto))
            output.println(gson.toJson(baseDto))
        }
    }

    private fun getServerAddressAsync() =
        backgroundScope.async(Dispatchers.IO) {
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
                    DatagramSocket().apply {
                        send(packet)
                        receive(packet)
                        close()
                    }
                    val message = String(packet.data).trim { it.code == 0 }
                    val udpDto = gson.fromJson(message, UdpDto::class.java)

                    udpDto.ip
                }
            }
                .onSuccess { ip -> Log.d(TAG, "Server IP: $ip") }
                .onFailure { error("Failed to get server address", it) }
                .getOrThrow()
        }

    private fun connectToServer(ip: String) =
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                Socket(ip, PORT_SERVER).apply {
                    socket = this
                    input = BufferedReader(InputStreamReader(getInputStream()))
                    output = PrintWriter(OutputStreamWriter(getOutputStream()))
                }
            }
                .onSuccess { Log.d(TAG, "Connected") }
                .onFailure { error("Connect to server failed", it) }
        }

    private fun getUserIdAsync(input: BufferedReader) =
        backgroundScope.async(Dispatchers.IO) {
            runCatching {
                val baseDto = gson.fromJson(input.readLine(), BaseDto::class.java)
                val connectedDto = gson.fromJson(baseDto.payload, ConnectedDto::class.java)
                connectedDto.id
            }
                .onSuccess { userId -> Log.d(TAG, "User ID: $userId") }
                .onFailure { error("Failed to get user id", it) }
                .getOrThrow()
        }

    private fun connectUser(output: PrintWriter, userId: String, userName: String) {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                val connectDto = ConnectDto(userId, userName)
                val baseDto = BaseDto(CONNECT, gson.toJson(connectDto))
                output.println(gson.toJson(baseDto))
            }
                .onSuccess { Log.d(TAG, "Sent user connect") }
                .onFailure { error("User connecting failed", it) }
        }
    }

    private suspend fun startPings(output: PrintWriter, userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val baseDto = BaseDto(PING, gson.toJson(PingDto(userId)))
            val pingMessage = gson.toJson(baseDto)
            while (true) {
                output.println(pingMessage)
                output.flush()
                delay(PING_DELAY)
            }
        }
    }

    private fun userRequest(output: PrintWriter, userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val baseDto = BaseDto(GET_USERS, gson.toJson(GetUsersDto(userId)))
            val getUsersMessage = gson.toJson(baseDto)
            output.println(getUsersMessage)
        }
    }

    private suspend fun freeingResources() {
        runCatching {
            withContext(NonCancellable) {
                input.close()
                output.close()
                socket.close()
            }
        }
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

    override fun disconnect() {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                freeingResources()
            }.getOrNull()
            backgroundScope.cancel()
        }
    }

    private fun error(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
        mutableConnectState.value = ConnectState.Error(message)
    }

    private companion object {
        val TAG: String = SocketRepository::class.java.simpleName
        const val EMULATOR_IP = "10.0.2.2"
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666

        const val TIMEOUT_BROADCAST = 5000L
        const val TIMEOUT_CONNECT_SERVER = 5000L
        const val TIMEOUT_GET_UID = 5000L

        const val PING_DELAY = 5000L
        const val USERS_REQUEST_DELAY = 3000L
    }
}