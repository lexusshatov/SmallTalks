package com.natife.example.domain.decorator

import com.example.core.dto.MessageDto
import com.example.core.repository.RemoteData
import com.example.core.repository.local.LocalData
import com.example.core.repository.local.Message
import com.example.core.userlist.UsersContract
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
    override val messages: Flow<MessageDto>
        get() = socketRepository.messages

    override suspend fun disconnect() =
        socketRepository.disconnect()

    override suspend fun saveMessage(message: Message) =
        localRepository.saveMessage(message)
}