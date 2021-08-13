package com.natife.example.domain.userlist

import com.natife.example.domain.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}