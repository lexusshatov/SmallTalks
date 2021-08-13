package com.natife.example.domain.repository.local

import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getDialog(receiver: User): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
}