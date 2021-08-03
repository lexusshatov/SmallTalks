package com.example.smalltalks.model.repository.remote

import com.example.smalltalks.view.chat.MessageItem

interface LocalChatContract: ChatContract {

    fun saveMessage(messageItem: MessageItem)
}