package com.example.smalltalks.model.repository.base.chat

import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.base.UserContract

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}