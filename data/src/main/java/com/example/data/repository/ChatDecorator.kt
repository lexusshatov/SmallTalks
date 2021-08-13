package com.example.data.repository

import com.natife.example.domain.chat.ChatRepository
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.LocalRepository
import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow

class ChatDecorator(
    private val localRepository: LocalRepository,
    private val socketRepository: ChatRepository
) : ChatRepository {

    override val me
        get() = socketRepository.me
    override val messages: Flow<Message>
        get() = socketRepository.messages

    override suspend fun sendMessage(to: User, message: String) {
        val messageDb = Message(
            from = me,
            to = to,
            message = message
        )
        localRepository.saveMessage(messageDb)
        socketRepository.sendMessage(to, message)
    }
}