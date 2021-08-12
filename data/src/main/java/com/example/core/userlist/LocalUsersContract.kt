package com.example.core.userlist

import com.example.core.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}