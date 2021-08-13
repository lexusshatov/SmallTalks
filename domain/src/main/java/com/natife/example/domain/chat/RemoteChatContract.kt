package com.natife.example.domain.chat

import com.natife.example.domain.UserContract
import com.natife.example.domain.dto.User

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}