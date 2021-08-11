package com.example.core.base.chat

import com.example.core.dto.User
import com.example.core.base.UserContract

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}