package com.natife.example.domain.base.chat

import com.natife.example.domain.base.UserContract
import com.natife.example.domain.base.dto.User

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}