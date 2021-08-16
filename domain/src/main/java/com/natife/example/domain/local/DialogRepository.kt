package com.natife.example.domain.local

import com.natife.example.domain.dto.User
import com.natife.example.domain.Message
import kotlinx.coroutines.flow.Flow

interface DialogRepository {

    fun getDialog(receiver: User): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
}