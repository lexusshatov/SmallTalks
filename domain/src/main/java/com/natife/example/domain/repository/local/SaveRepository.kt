package com.natife.example.domain.repository.local

interface SaveRepository {

    suspend fun saveMessage(message: Message)
}