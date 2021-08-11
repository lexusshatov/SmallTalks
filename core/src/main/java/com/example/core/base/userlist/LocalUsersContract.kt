package com.example.core.base.userlist

import com.example.core.base.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}