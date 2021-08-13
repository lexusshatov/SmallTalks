package com.example.data.decorator

import com.natife.example.domain.repository.local.LocalData
import com.natife.example.domain.repository.local.Message
import com.natife.example.domain.repository.remote.RemoteData
import com.natife.example.domain.userlist.UsersContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val localRepository: LocalData,
    private val socketRepository: RemoteData
) : UsersContract {

    override val me
        get() = socketRepository.me
    override val users
        get() = socketRepository.users
    override val messages: Flow<Message>
        get() = socketRepository.messages

    override suspend fun disconnect() =
        socketRepository.disconnect()

    override suspend fun saveMessage(message: Message) =
        localRepository.saveMessage(message)
}