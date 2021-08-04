package com.example.smalltalks.model.repository.decorator

import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.local.LocalData
import com.example.smalltalks.model.repository.local.Message
import com.example.smalltalks.model.repository.remote.RemoteData
import kotlinx.coroutines.flow.Flow

class RepositoryDecorator(
    private val localRepository: LocalData,
    private val socketRepository: RemoteData
) : DataRepository {

    override val connect = socketRepository.connect
    override val me
        get() = socketRepository.me
    override val users
        get() = socketRepository.users
    override val messages: Flow<MessageDto>
        get() = socketRepository.messages

    override fun connect(userName: String) =
        socketRepository.connect(userName)

    override fun disconnect() =
        socketRepository.disconnect()

    override fun sendMessage(to: String, message: String) =
        socketRepository.sendMessage(to, message)

    override fun getMessages() =
        localRepository.getMessages()

    override fun saveMessage(message: Message) =
        localRepository.saveMessage(message)

    override fun getDialog(user1: User, user2: User) =
        localRepository.getDialog(user1, user2)
}