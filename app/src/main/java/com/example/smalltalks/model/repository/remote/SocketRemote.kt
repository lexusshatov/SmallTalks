package com.example.smalltalks.model.repository.remote

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smalltalks.model.remote_protocol.*
import com.example.smalltalks.view.chat.MessageItem
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import javax.inject.Inject

class SocketRemote @Inject constructor(
    private val gson: Gson
) : RemoteData {
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    private var timeoutPong = 15000L

    private val backgroundScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    override lateinit var me: User

    private val mutableMessages = MutableSharedFlow<MessageItem>()
    override val messages: SharedFlow<MessageItem>
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
        get() {
            userRequest(output, me.id)
            return mutableUsers
        }

    private val mutableConnect = MutableLiveData<Boolean>()
    override val connect: LiveData<Boolean>
        get() = mutableConnect

    override fun connect(userName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            runCatching {
                // FIXME: 30.07.2021
                val ip =
                    "192.168.88.26"//withTimeout(TIMEOUT_BROADCAST) { getServerAddressAsync().await() }
                //init socket, in/out streams
                withTimeout(TIMEOUT_CONNECT_SERVER) { connectToServer(ip).join() }
                val userId = withTimeout(TIMEOUT_GET_UID) { getUserIdAsync(input).await() }
                me = User(userId, userName)
                connectUser(output, userId, userName)
                mutableConnect.postValue(true)

                startPings(output, userId)
                startListening(input)
            }
                .onFailure {
                    mutableConnect.postValue(false)
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
            disconnect()
            connect(me.name)
        }
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                while (true) {
                    val baseDto = gson.fromJson(input.readLine(), BaseDto::class.java)
                    when (baseDto.action) {
                        BaseDto.Action.NEW_MESSAGE -> {
                            val newMessageDto =
                                gson.fromJson(baseDto.payload, MessageDto::class.java)
                            val messageItem = MessageItem(newMessageDto, false)
                            mutableMessages.emit(messageItem)
                        }
                        BaseDto.Action.USERS_RECEIVED -> {
                            val usersReceivedDto =
                                gson.fromJson(baseDto.payload, UsersReceivedDto::class.java)
                            mutableUsers.postValue(usersReceivedDto.users)
                        }
                        BaseDto.Action.PONG -> {
                            timeoutPong = 15000L
                        }
                    }
                }
            }
        }
    }

    override fun sendMessage(to: String, message: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val sendMessageDto = SendMessageDto(me.id, to, message)
            val baseDto = BaseDto(BaseDto.Action.SEND_MESSAGE, gson.toJson(sendMessageDto))
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
                .onFailure { throwable -> Log.e(TAG, "Failed to get server address", throwable) }
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
                .onFailure { throwable -> Log.e(TAG, "Connect to server failed", throwable) }
        }

    private fun getUserIdAsync(input: BufferedReader) =
        backgroundScope.async(Dispatchers.IO) {
            runCatching {
                val baseDto = gson.fromJson(input.readLine(), BaseDto::class.java)
                val connectedDto = gson.fromJson(baseDto.payload, ConnectedDto::class.java)
                connectedDto.id
            }
                .onSuccess { userId -> Log.d(TAG, "User ID: $userId") }
                .onFailure { throwable -> Log.e(TAG, "Failed to get user id", throwable) }
                .getOrThrow()
        }

    private fun connectUser(output: PrintWriter, userId: String, userName: String) {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                val connectDto = ConnectDto(userId, userName)
                val baseDto = BaseDto(BaseDto.Action.CONNECT, gson.toJson(connectDto))
                output.println(gson.toJson(baseDto))
            }
                .onSuccess { Log.d(TAG, "Sent user connect") }
                .onFailure { throwable -> Log.e(TAG, "User connecting failed", throwable) }
        }
    }

    private suspend fun startPings(output: PrintWriter, userId: String) {
        backgroundScope.launch(Dispatchers.IO) {
            val baseDto = BaseDto(BaseDto.Action.PING, gson.toJson(PingDto(userId)))
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
            val baseDto = BaseDto(BaseDto.Action.GET_USERS, gson.toJson(GetUsersDto(userId)))
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

    fun disconnect() {
        backgroundScope.launch(Dispatchers.IO) {
            runCatching {
                freeingResources()
            }.getOrNull()
            backgroundScope.cancel()
        }
    }

    private companion object {
        val TAG: String = SocketRemote::class.java.simpleName
        const val EMULATOR_IP = "10.0.2.2"
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666

        const val TIMEOUT_BROADCAST = 5000L
        const val TIMEOUT_CONNECT_SERVER = 5000L
        const val TIMEOUT_GET_UID = 5000L

        const val PING_DELAY = 5000L
        const val USERS_REQUEST_DELAY = 7000L
    }
}