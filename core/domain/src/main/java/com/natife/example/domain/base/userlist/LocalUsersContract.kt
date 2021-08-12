package com.natife.example.domain.base.userlist

import com.natife.example.domain.base.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}