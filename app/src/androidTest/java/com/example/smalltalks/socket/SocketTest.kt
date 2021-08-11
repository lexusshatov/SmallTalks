package com.example.smalltalks.socket

import com.example.smalltalks.model.remote_protocol.*
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

private const val HOST = "255.255.255.255"
private const val PORT_BROADCAST = 8888
private const val PORT_SERVER = 6666

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

    val udpDto = gson.fromJson(message, com.example.domain.remote_protocol.UdpDto::class.java)
    println(udpDto)

    //connect to server with gained IP:6666
    //and request UID
    val socket = Socket(udpDto.ip, PORT_SERVER)
    val input = BufferedReader(InputStreamReader(socket.getInputStream()))
    val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

    val baseDto = gson.fromJson(input.readLine(), com.example.domain.remote_protocol.BaseDto::class.java)
    println(baseDto)
    val connectedDto = gson.fromJson(baseDto.payload, com.example.domain.remote_protocol.ConnectedDto::class.java)
    println(connectedDto)

    //connect to server with gained UID
    val connectDto = com.example.domain.remote_protocol.ConnectDto(connectedDto.id, "Oleh")
    val baseConnectDto = com.example.domain.remote_protocol.BaseDto(
        com.example.domain.remote_protocol.BaseDto.Action.CONNECT,
        gson.toJson(connectDto)
    )
    output.println(gson.toJson(baseConnectDto))
    output.flush()


    //get users
    val getUsersDto = com.example.domain.remote_protocol.GetUsersDto(connectedDto.id)
    val baseGetUsersDto = com.example.domain.remote_protocol.BaseDto(
        com.example.domain.remote_protocol.BaseDto.Action.GET_USERS,
        gson.toJson(getUsersDto)
    )
    output.println(gson.toJson(baseGetUsersDto))
    output.flush()
    val usersDto = input.readLine().apply { println(this) }
    val baseUsersReceivedDto = gson.fromJson( usersDto, com.example.domain.remote_protocol.BaseDto::class.java)
    val usersReceivedDto = gson.fromJson(baseUsersReceivedDto.payload, com.example.domain.remote_protocol.UsersReceivedDto::class.java)

    //send message
    val sendMessageDto = com.example.domain.remote_protocol.SendMessageDto(
        connectedDto.id,
        "8486d8a4-0674-48b6-a18a-8cee636e963c",
        "Hello"
    )
    val baseSendMessageDto = com.example.domain.remote_protocol.BaseDto(
        com.example.domain.remote_protocol.BaseDto.Action.SEND_MESSAGE,
        gson.toJson(sendMessageDto)
    )
    output.println(gson.toJson(baseSendMessageDto))
    output.flush()

    //get message
    val baseMessageDto = gson.fromJson(input.readLine(), com.example.domain.remote_protocol.BaseDto::class.java)
    val messageDto = gson.fromJson(baseMessageDto.payload, com.example.domain.remote_protocol.MessageDto::class.java)
    println(messageDto.message)

    thread(start = true) {
        println("start ping")
        while (true) {
            val ping = com.example.domain.remote_protocol.PingDto(connectedDto.id)
            val message = gson.toJson(
                com.example.domain.remote_protocol.BaseDto(
                    com.example.domain.remote_protocol.BaseDto.Action.PING,
                    gson.toJson(ping)
                )
            )
            output.println(message)
            output.flush()
            Thread.sleep(5000)
        }
    }
}
