package com.example.core.chat

import com.example.core.UserContract
import com.example.core.dto.User

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}