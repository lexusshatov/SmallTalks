package com.natife.example.domain.chat

import com.natife.example.domain.dto.User

interface SendMessage {

    suspend fun sendMessage(to: User, message: String)
}
