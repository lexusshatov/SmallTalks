package com.example.smalltalks

import com.example.smalltalks.model.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import kotlin.concurrent.thread

const val HOST = "255.255.255.255"
const val PORT_BROADCAST = 8888
const val PORT_SERVER = 6666

fun main() {
    val gson = Gson()

    //broadcast send to 8888 and getting server IP
    val buffer = ByteArray(100)
    val packet = DatagramPacket(
        buffer,
        buffer.size,
        InetAddress.getByName(HOST),
        PORT_BROADCAST
    )
    val datagramSocket = DatagramSocket()
    datagramSocket.send(packet)
    datagramSocket.receive(packet)
    val message = String(packet.data).trim { it.code == 0 }

    val udpDto = gson.fromJson(message, UdpDto::class.java)
    println(udpDto)

    //connect to server with gained IP:6666
    //and request UID
    val socket = Socket(udpDto.ip, PORT_SERVER)
    val input = BufferedReader(InputStreamReader(socket.getInputStream()))
    val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

    val baseDto = gson.fromJson(input.readLine(), BaseDto::class.java)
    println(baseDto)
    val connectedDto = gson.fromJson(baseDto.payload, ConnectedDto::class.java)
    println(connectedDto)

    //connect to server with gained UID
    val connectDto = ConnectDto(connectedDto.id, "Oleh")
    val baseConnectDto = BaseDto(BaseDto.Action.CONNECT, gson.toJson(connectDto))
    output.println(gson.toJson(baseConnectDto))
    output.flush()


    thread(start = true) {
        println("start ping")
        while (true) {
            val ping = PingDto(connectedDto.id)
            val message = gson.toJson(BaseDto(BaseDto.Action.PING, payload = gson.toJson(ping)))
            output.println(message)
            output.flush()
            Thread.sleep(5000)
        }
    }

    //get users
    val getUsersDto = GetUsersDto(connectedDto.id)
    val baseGetUsersDto = BaseDto(BaseDto.Action.GET_USERS, gson.toJson(getUsersDto))
    output.println(gson.toJson(baseGetUsersDto))
    output.flush()
    println(input.readLine())
}
