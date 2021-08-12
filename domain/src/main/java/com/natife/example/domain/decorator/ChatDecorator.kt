package com.natife.example.domain.decorator

import com.example.core.chat.ChatContract
import com.example.core.dto.MessageDto
import com.example.core.dto.User
import com.example.core.repository.RemoteData
import com.example.core.repository.local.LocalData
import com.example.core.repository.local.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDecorator @Inject constructor(
    private val localRepository: LocalData,
    private val socketRepository: RemoteData
) : ChatContract {

    override val me
        get() = socketRepository.me
    override val messages: Flow<MessageDto>
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

    override fun getDialog(receiver: User) = localRepository.getDialog(receiver)
}