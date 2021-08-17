package com.example.data.repository

import com.natife.example.domain.Message
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.MessageRepository
import com.natife.example.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageDecorator @Inject constructor(
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
        messageRepository.saveMessage(messageDb)
        messageRepository.sendMessage(to, message)
    }

    override fun getDialog(receiver: User) =
        messageRepository.getDialog(receiver)

    override suspend fun saveMessage(message: Message) =
        messageRepository.saveMessage(message)
}