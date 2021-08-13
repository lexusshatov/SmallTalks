package com.natife.example.domain.chat

import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    val messages: Flow<Message>
}
