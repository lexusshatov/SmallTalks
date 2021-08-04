package com.example.smalltalks.model.repository.base

import com.example.smalltalks.model.remote_protocol.MessageDto
import kotlinx.coroutines.flow.Flow

interface ChatContract: UserContract {
    val messages: Flow<MessageDto>
    fun sendMessage(to: String, message: String)
}