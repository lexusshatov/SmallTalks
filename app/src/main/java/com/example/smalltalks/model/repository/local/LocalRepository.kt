package com.example.smalltalks.model.repository.local

import com.example.smalltalks.view.chat.MessageItem
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val dao: MessageDao
) {

    fun getMessages() = dao.getAll().map { it.messageItem }

    fun addMessage(messageItem: MessageItem) = dao.addMessage(Message(0, messageItem))
}