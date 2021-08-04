package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User
import com.google.gson.Gson
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val gson: Gson,
    private val dao: MessageDao,
) : LocalData {

    override fun getMessages(): LiveData<List<Message>> {
        return dao.getAll()
    }

    override fun saveMessage(message: Message) {
        dao.addMessage(message)
    }

    override fun getDialog(user1: User, user2: User): LiveData<List<Message>> {
        val user1json = gson.toJson(user1)
        val user2json = gson.toJson(user2)
        return dao.getDialog(user1json, user2json)
    }
}
