package com.example.data.decorator

import com.natife.example.domain.chat.ChatContract
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.LocalData
import com.natife.example.domain.repository.local.Message
import com.natife.example.domain.repository.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDecorator @Inject constructor(
    private val localRepository: LocalData,
    private val socketRepository: RemoteData
) : ChatContract {

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

    override fun getDialog(receiver: User) = localRepository.getDialog(receiver)
}