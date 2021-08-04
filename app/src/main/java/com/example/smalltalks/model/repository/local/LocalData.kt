package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import com.example.smalltalks.view.chat.MessageItem

interface LocalData {

    fun getMessages(): LiveData<List<Message>>

    fun saveMessage(messageItem: MessageItem)
}