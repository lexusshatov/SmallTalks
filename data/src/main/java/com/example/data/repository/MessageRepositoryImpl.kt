package com.example.data.repository

import com.example.data.repository.local.data.MessageDao
import com.example.data.repository.local.data.toDatabase
import com.example.data.repository.local.data.toMessage
import com.example.data.repository.remote.SocketHolder
import com.google.gson.Gson
import com.natife.example.domain.Message
import com.natife.example.domain.dto.BaseDto
import com.natife.example.domain.dto.SendMessageDto
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.MessageRepository
import com.natife.example.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val dao: MessageDao,
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

    override suspend fun saveMessage(message: Message) {
        dao.addMessage(message.toDatabase())
    }

    override fun getDialog(receiver: User): Flow<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
            .map { list ->
                list.map {
                    it.toMessage()
                }
            }
    }
}