package com.example.smalltalks.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.remote_protocol.*
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val gson: Gson
) : BaseViewModel() {
    private lateinit var socket: Socket
    lateinit var input: BufferedReader
    lateinit var output: PrintWriter

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    private val mutableData = MutableLiveData<Boolean>()
    override val data: LiveData<Boolean>
        get() = mutableData

    lateinit var user: User

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                // FIXME: 30.07.2021  
                val ip = withTimeout(TIMEOUT_BROADCAST) { getServerAddressAsync().await() }
                //init socket, in/out streams
                withTimeout(TIMEOUT_CONNECT_SERVER) { connectToServer(ip).join() }
                val userId = withTimeout(TIMEOUT_GET_UID) { getUserIdAsync(input).await() }
                user = User(userId, userName)
                connectUser(output, userId, userName)
                startPings(output, userId)
            }
                .onSuccess { mutableData.postValue(true) }
                .onFailure {
                    Log.e(TAG, it.toString())
                    mutableData.postValue(false)
                    freeingResources()
                }
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
                delay(5000)
            }
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

    private companion object {
        val TAG: String = UserListViewModel::class.java.simpleName
        const val EMULATOR_IP = "10.0.2.2"
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666

        const val TIMEOUT_BROADCAST = 5000L
        const val TIMEOUT_CONNECT_SERVER = 5000L
        const val TIMEOUT_GET_UID = 5000L
    }

    override fun onCleared() {
        super.onCleared()
        backgroundScope.launch {
            freeingResources()
        }
        backgroundScope.cancel()
    }
}