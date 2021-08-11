package com.natife.example.domain.decorator

import com.example.core.base.repository.RemoteData
import com.example.core.base.repository.decorator.DataRepository
import com.example.core.base.repository.local.LocalData
import com.example.core.base.repository.local.Message
import com.natife.example.domain.dto.MessageDto
import com.natife.example.domain.dto.User
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

    override fun deleteUserName() = localRepository.deleteUserName()

    override fun getUserName() = localRepository.getUserName()

    override fun saveUserName(userName: String) = localRepository.saveUserName(userName)

    override fun getDialog(receiver: User) =
        localRepository.getDialog(receiver)
}