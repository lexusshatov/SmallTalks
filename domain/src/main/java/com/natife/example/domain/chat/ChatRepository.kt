package com.natife.example.domain.chat

import com.natife.example.domain.UserRepository
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository : UserRepository {

    val messages: Flow<Message>
    suspend fun sendMessage(to: User, message: String)
}