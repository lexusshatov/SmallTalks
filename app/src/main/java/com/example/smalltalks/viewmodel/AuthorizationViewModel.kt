package com.example.smalltalks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smalltalks.model.*
import com.example.smalltalks.view.authorization.ContractAuthorizationView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket

class AuthorizationViewModel(
    private var userId: String?,
    private val contractAuthorizationView: ContractAuthorizationView
) : ViewModel() {
    private val gson = Gson()
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    suspend fun connect(userName: String) {
        try {
            println("TUT")
            println(getServerIp())
            val inout = connectToServer(getServerIp())
            input = inout.first
            output = inout.second
            if (userId == null) {
                //get user id from server
                userId = gson.fromJson(
                    gson.fromJson(
                        input.readLine(),
                        BaseDto::class.java
                    ).payload,
                    ConnectedDto::class.java
                ).id
                contractAuthorizationView.saveUserId(userId!!)
            }
            //connect with ID + name
            output.println(
                gson.toJson(
                    BaseDto(
                        BaseDto.Action.CONNECT,
                        gson.toJson(
                            ConnectDto(userId!!, userName)
                        )
                    )
                )
            )
            output.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun disconnect() {
        try {
            val disconnectDto = BaseDto(
                BaseDto.Action.DISCONNECT,
                gson.toJson(
                    DisconnectDto(
                        userId!!,
                        0
                    )
                )
            )
            output.println(gson.toJson(disconnectDto))
            output.flush()
            input.close()
            output.close()
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //broadcast send to 8888 and getting server IP
    private fun getServerIp(): String {
        var message: String? = null
        try {
            val buffer = ByteArray(30)
            val packet = DatagramPacket(
                buffer,
                buffer.size,
                InetAddress.getByName(HOST_BROADCAST),
                PORT_BROADCAST
            )
            val datagramSocket = DatagramSocket()
            datagramSocket.send(packet)
            datagramSocket.receive(packet)
            message = String(packet.data).trim { it.code == 0 }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gson.fromJson(message, UdpDto::class.java).ip
    }

    //connect to server with gained_IP:6666
    private fun connectToServer(ip: String): Pair<BufferedReader, PrintWriter> {
        val socket = Socket(ip, PORT_SERVER)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

        return Pair(input, output)
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    private companion object {
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666
    }

    class Factory {

        companion object {
            fun create(userId: String?, contractAuthorizationView: ContractAuthorizationView) =
                AuthorizationViewModel(userId, contractAuthorizationView)
        }
    }
}