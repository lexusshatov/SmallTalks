package com.example.smalltalks.model.repository

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.base.repository.LocalData
import com.example.smalltalks.model.repository.local.Message
import com.example.smalltalks.model.repository.local.MessageDao
import com.google.gson.Gson
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val gson: Gson,
    private val dao: MessageDao,
) : LocalData {


    override suspend fun saveMessage(message: Message) {
        dao.addMessage(message)
    }

    override fun getDialog(receiver: User): LiveData<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
    }
}
