package com.example.data.repository

import com.natife.example.domain.Message
import com.natife.example.domain.remote.MessageRepository
import com.natife.example.domain.dto.User
import com.natife.example.domain.local.DialogRepository
import com.natife.example.domain.remote.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageDecorator @Inject constructor(
    private val dialogRepository: DialogRepository,
    private val messageRepository: MessageRepository,
    private val usersRepository: UsersRepository
) : MessageRepository {

    override val messages: Flow<Message>
        get() = messageRepository.messages

    override suspend fun sendMessage(to: User, message: String) {
        val messageDb = Message(
            from = usersRepository.me,
            to = to,
            message = message
        )
        dialogRepository.saveMessage(messageDb)
        messageRepository.sendMessage(to, message)
    }
}