package com.example.smalltalks.model.repository.decorator

import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.base.repository.LocalData
import com.example.smalltalks.model.repository.local.Message
import com.example.smalltalks.model.repository.base.repository.RemoteData
import kotlinx.coroutines.flow.Flow

class RepositoryDecorator(
    private val localRepository: LocalData,
    private val socketRepository: RemoteData
) : DataRepository {

    override val connectState = socketRepository.connectState
    override val me
        get() = socketRepository.me
    override val users
        get() = socketRepository.users
    override val messages: Flow<MessageDto>
        get() = socketRepository.messages

    override suspend fun connect(userName: String) =
        socketRepository.connect(userName)

    override suspend fun disconnect() =
        socketRepository.disconnect()

    override suspend fun sendMessage(to: User, message: String) {
        val messageDb = Message(
            from = me,
            to = to,
            message = message
        )
        saveMessage(messageDb)
        socketRepository.sendMessage(to, message)
    }

    override suspend fun saveMessage(message: Message) =
        localRepository.saveMessage(message)

    override fun getDialog(receiver: User) =
        localRepository.getDialog(receiver)
}