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
import kotlin.concurrent.thread

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val gson: Gson
) : BaseViewModel() {
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    private val mutableData = MutableLiveData<Boolean>()
    override val data: LiveData<Boolean>
        get() = mutableData

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            runCatching {
                val ip = withTimeout(TIMEOUT_BROADCAST) { getServerAddressAsync().await() }
                Log.d(TAG, "Server IP: $ip")

                //init socket, in/out streams
                withTimeout(TIMEOUT_CONNECT_SERVER) { connectToServerAsync(ip).join() }
                Log.d(TAG, "Connected")

                val userId = withTimeout(TIMEOUT_GET_UID) { getUserIdAsync(input).await() }
                Log.d(TAG, "User ID: $userId")

                connectUser(output, userId, userName)
                startPings(output, userId)
            }
                .onSuccess { mutableData.postValue(true) }
                .onFailure { throwable ->
                    Log.e(TAG, "Connect failed", throwable)
                    mutableData.postValue(false)
                    runCatching {
                        input.close()
                        output.close()
                        socket.close()
                    }.onFailure { Log.e(TAG, "Freeing resources failed", it) }
                }
        }
    }

    private suspend fun getServerAddressAsync() =
        coroutineScope {
            async(Dispatchers.IO) {
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
                }.getOrThrow()
            }
        }

    private suspend fun connectToServerAsync(ip: String) =
        coroutineScope {
            launch(Dispatchers.IO) {
                runCatching {
                    Socket(ip, PORT_SERVER).apply {
                        socket = this
                        input = BufferedReader(InputStreamReader(getInputStream()))
                        output = PrintWriter(OutputStreamWriter(getOutputStream()))
                    }
                }.onFailure { throwable -> throw throwable }
            }
        }


    private suspend fun getUserIdAsync(input: BufferedReader) =
        coroutineScope {
            async(Dispatchers.IO) {
                runCatching {
                    gson.fromJson(
                        gson.fromJson(
                            input.readLine(),
                            BaseDto::class.java
                        ).payload,
                        ConnectedDto::class.java
                    ).id
                }.getOrThrow()
            }
        }


    private suspend fun connectUser(output: PrintWriter, userId: String, userName: String) {
        coroutineScope {
            launch(Dispatchers.IO) {
                runCatching {
                    output.println(
                        gson.toJson(
                            BaseDto(
                                BaseDto.Action.CONNECT,
                                gson.toJson(
                                    ConnectDto(
                                        userId,
                                        userName
                                    )
                                )
                            )
                        )
                    )
                }.exceptionOrNull()
            }
        }
    }

    private fun startPings(output: PrintWriter, userId: String) {
        thread(start = true) {
            while (true) {
                val pingMessage = gson.toJson(
                    BaseDto(
                        BaseDto.Action.PING,
                        gson.toJson(PingDto(userId))
                    )
                )
                output.println(pingMessage)
                output.flush()
                Thread.sleep(5000)
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
        const val TIMEOUT_GET_UID = 3000L
    }
}