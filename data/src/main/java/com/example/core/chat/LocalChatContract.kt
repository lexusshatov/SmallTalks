package com.example.core.chat

import androidx.lifecycle.LiveData
import com.example.core.dto.User
import com.example.core.repository.local.Message

interface LocalChatContract {

    fun getDialog(receiver: User): LiveData<List<Message>>
}