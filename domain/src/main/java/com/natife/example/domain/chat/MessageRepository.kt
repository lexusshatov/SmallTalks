package com.natife.example.domain.chat

import com.natife.example.domain.dto.User
import com.natife.example.domain.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    val messages: Flow<Message>
    suspend fun sendMessage(to: User, message: String)
}
