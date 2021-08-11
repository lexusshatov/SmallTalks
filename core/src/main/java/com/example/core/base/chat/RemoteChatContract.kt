package com.example.core.base.chat

import com.example.core.di.decorator.base.UserContract
import com.natife.example.domain.dto.User

interface RemoteChatContract : UserContract {

    suspend fun sendMessage(to: User, message: String)
}