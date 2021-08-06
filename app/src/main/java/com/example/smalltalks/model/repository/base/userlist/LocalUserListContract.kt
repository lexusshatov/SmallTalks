package com.example.smalltalks.model.repository.base.userlist

import com.example.smalltalks.model.repository.local.Message
//TODO RENAME
interface LocalUserListContract {

    suspend fun saveMessage(message: Message)
}