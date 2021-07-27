package com.example.smalltalks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smalltalks.model.BaseDto
import com.example.smalltalks.model.ConnectDto
import com.example.smalltalks.model.ConnectedDto
import com.example.smalltalks.model.UdpDto
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
    private var userId: String?
) : ViewModel() {
    private val gson = Gson()

    fun connect(userName: String) {
        val inout = connectToServer(getServerIp())
        val input = inout.first
        val output = inout.second
        if (userId == null) {
            //get user id from server
            userId = gson.fromJson(
                gson.fromJson(
                    input.readLine(),
                    BaseDto::class.java
                ).payload,
                ConnectedDto::class.java
            ).id
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
    }

    //broadcast send to 8888 and getting server IP
    private fun getServerIp(): String {
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
        val message = String(packet.data).trim { it.code == 0 }

        return gson.fromJson(message, UdpDto::class.java).ip
    }

    //connect to server with gained_IP:6666
    private fun connectToServer(ip: String): Pair<BufferedReader, PrintWriter> {
        val socket = Socket(ip, PORT_SERVER)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

        return Pair(input, output)
    }

    private companion object {
        const val HOST_BROADCAST = "255.255.255.255"
        const val PORT_BROADCAST = 8888
        const val PORT_SERVER = 6666
    }

    class Factory {

        companion object {
            fun create(userId: String?) = AuthorizationViewModel(userId)
        }
    }
}