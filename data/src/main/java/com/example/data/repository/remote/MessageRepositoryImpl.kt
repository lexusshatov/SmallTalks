package com.example.data.repository.remote

import com.google.gson.Gson
import com.natife.example.domain.Message
import com.natife.example.domain.remote.MessageRepository
import com.natife.example.domain.dto.BaseDto
import com.natife.example.domain.dto.SendMessageDto
import com.natife.example.domain.dto.User
import com.natife.example.domain.remote.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val socketHolder: SocketHolder,
    private val usersRepository: UsersRepository
) : MessageRepository {

    override val messages: Flow<Message>
        get() = socketHolder.messages

    override suspend fun sendMessage(to: User, message: String) {
        val sendMessageDto = SendMessageDto(usersRepository.me.id, to.id, message)
        val baseDtoSend = BaseDto(
            BaseDto.Action.SEND_MESSAGE,
            gson.toJson(sendMessageDto)
        )
        val baseDtoSendJson = gson.toJson(baseDtoSend)
        runCatching {
            socketHolder.sendMessageToServer(baseDtoSendJson)
        }
    }
}