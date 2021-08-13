package com.example.data.repository.local

import com.example.data.repository.local.data.MessageDao
import com.example.data.repository.local.data.MessageDb
import com.google.gson.Gson
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.LocalRepository
import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepositoryImpl(
    private val gson: Gson,
    private val dao: MessageDao,
) : LocalRepository {

    override suspend fun saveMessage(message: Message) {
        val messageDb = MessageDb(
            from = message.from,
            to = message.to,
            message = message.message
        )
        dao.addMessage(messageDb)
    }

    override fun getDialog(receiver: User): Flow<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
            .map { list ->
                list.map {
                    Message(
                        from = it.from,
                        to = it.to,
                        message = it.message
                    )
                }
            }
    }


    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"
    }
}
