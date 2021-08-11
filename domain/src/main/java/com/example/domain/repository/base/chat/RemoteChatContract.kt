package com.example.domain.repository.base.chat

import com.example.domain.repository.base.UserContract

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: com.example.domain.remote_protocol.User, message: String)
}