package com.example.smalltalks.model.repository.base.chat

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.local.Message

interface LocalChatContract {

    fun getDialog(user1: User, user2: User): LiveData<List<Message>>
}