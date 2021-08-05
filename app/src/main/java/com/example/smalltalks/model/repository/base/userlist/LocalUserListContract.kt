package com.example.smalltalks.model.repository.base.userlist

import com.example.smalltalks.model.repository.local.Message

interface LocalUserListContract {

    suspend fun saveMessage(message: Message)
}