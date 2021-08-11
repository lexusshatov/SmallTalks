package com.example.core.base.chat

import androidx.lifecycle.LiveData
import com.example.core.base.repository.local.Message
import com.natife.example.domain.dto.User

interface LocalChatContract {

    fun getDialog(receiver: User): LiveData<List<Message>>
}