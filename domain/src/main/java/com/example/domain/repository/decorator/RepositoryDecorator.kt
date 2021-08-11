package com.example.domain.repository.decorator

import com.example.domain.repository.base.repository.LocalData
import com.example.domain.repository.base.repository.RemoteData
import com.example.domain.repository.local.Message
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
    override val messages: Flow<com.example.domain.remote_protocol.MessageDto>
        get() = socketRepository.messages

    override suspend fun connect(userName: String) =
        socketRepository.connect(userName)

    override suspend fun disconnect() =
        socketRepository.disconnect()

    override suspend fun sendMessage(to: com.example.domain.remote_protocol.User, message: String) {
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

    override fun deleteUserName() = localRepository.deleteUserName()

    override fun getUserName() = localRepository.getUserName()

    override fun saveUserName(userName: String) = localRepository.saveUserName(userName)

    override fun getDialog(receiver: com.example.domain.remote_protocol.User) =
        localRepository.getDialog(receiver)
}