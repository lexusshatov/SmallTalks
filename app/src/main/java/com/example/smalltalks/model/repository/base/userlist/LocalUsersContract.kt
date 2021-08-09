package com.example.smalltalks.model.repository.base.userlist

import com.example.smalltalks.model.repository.local.Message

interface LocalUsersContract {

    suspend fun saveMessage(message: Message)
}