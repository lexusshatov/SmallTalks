package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import com.example.smalltalks.view.chat.MessageItem
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val dao: MessageDao
) : LocalData {

    override fun getMessages(): LiveData<List<Message>> {
        return dao.getAll()
    }

    override fun saveMessage(messageItem: MessageItem) {
        dao.addMessage(Message(0, messageItem))
    }
}
