package com.natife.example.domain.base.chat

import androidx.lifecycle.LiveData
import com.natife.example.domain.base.dto.User
import com.natife.example.domain.base.repository.local.Message

interface LocalChatContract {

    fun getDialog(receiver: User): LiveData<List<Message>>
}