package com.example.domain.repository.base.chat

import androidx.lifecycle.LiveData
import com.example.domain.repository.local.Message

interface LocalChatContract {

    fun getDialog(receiver: com.example.domain.remote_protocol.User): LiveData<List<Message>>
}