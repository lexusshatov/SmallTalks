package com.example.smalltalks.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.remote_protocol.BaseDto
import com.example.smalltalks.model.remote_protocol.ConnectDto
import com.example.smalltalks.model.remote_protocol.ConnectedDto
import com.example.smalltalks.model.remote_protocol.UdpDto
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

    private val mutableData = MutableLiveData<Boolean>()
    override val data: LiveData<Boolean>
        get() = mutableData

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val ip = withTimeout(TIMEOUT_BROADCAST) { getServerIpAsync().await() }
                    .getOrThrow()
                Log.d(TAG, "Server IP: $ip")

                val socket = withTimeout(TIMEOUT_CONNECT_SERVER) { connectToServerAsync(ip).await() }
                    .getOrThrow()
                Log.d(TAG, "test")
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

                val userId = withTimeout(TIMEOUT_GET_UID) { getUserIdAsync(input).await() }
                    .getOrThrow()
                Log.d(TAG, "User ID: $userId")

                connectUserAsync(output, userId, userName)
                mutableData.postValue(true)
            } catch (e: Exception) {
                mutableData.postValue(false)
                e.printStackTrace()
            }
        }
    }

    private fun getServerIpAsync() =
        GlobalScope.async {
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
    }

    private fun connectToServerAsync(ip: String) =
        GlobalScope.async {
            runCatching {
                Socket(ip, PORT_SERVER)
            }
        }


    private fun getUserIdAsync(input: BufferedReader) =
        GlobalScope.async {
            runCatching {
                gson.fromJson(
                    gson.fromJson(
                        input.readLine(),
                        BaseDto::class.java
                    ).payload,
                    ConnectedDto::class.java
                ).id
            }
        }

    private fun connectUserAsync(output: PrintWriter, userId: String, userName: String) {
        GlobalScope.async {
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

        const val TIMEOUT_BROADCAST = 3000L
        const val TIMEOUT_CONNECT_SERVER = 5000L
        const val TIMEOUT_GET_UID = 3000L
    }
}