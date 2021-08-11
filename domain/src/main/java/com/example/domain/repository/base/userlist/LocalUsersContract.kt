package com.example.domain.repository.base.userlist

import com.example.domain.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}