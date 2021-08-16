package com.example.data.repository.local

import com.example.data.repository.local.data.MessageDao
import com.example.data.repository.local.data.toDatabase
import com.example.data.repository.local.data.toMessage
import com.google.gson.Gson
import com.natife.example.domain.dto.User
import com.natife.example.domain.local.DialogRepository
import com.natife.example.domain.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DialogRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val dao: MessageDao,
) : DialogRepository {

    override suspend fun saveMessage(message: Message) {
        dao.addMessage(message.toDatabase())
    }

    override fun getDialog(receiver: User): Flow<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
            .map { list ->
                list.map {
                    it.toMessage()
                }
            }
    }


    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"
    }
}
