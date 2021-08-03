package com.example.smalltalks.model.repository.remote

import com.example.smalltalks.view.chat.MessageItem
import kotlinx.coroutines.flow.Flow

interface ChatContract: UserContract {
    val messages: Flow<MessageItem>
    fun sendMessage(to: String, message: String)
}