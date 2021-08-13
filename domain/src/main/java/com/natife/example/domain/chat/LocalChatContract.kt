package com.natife.example.domain.chat

import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow

interface LocalChatContract {

    fun getDialog(receiver: User): Flow<List<Message>>
}