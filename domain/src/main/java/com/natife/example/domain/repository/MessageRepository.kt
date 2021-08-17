package com.natife.example.domain.repository

import com.natife.example.domain.dto.User
import com.natife.example.domain.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    val messages: Flow<Message>
    suspend fun sendMessage(to: User, message: String)
    fun getDialog(receiver: User): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
}
