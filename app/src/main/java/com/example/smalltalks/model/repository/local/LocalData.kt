package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User

interface LocalData {

    fun getMessages(): LiveData<List<Message>>

    fun saveMessage(message: Message)

    fun getDialog(user1: User, user2: User): LiveData<List<Message>>
}