package com.example.domain.repository.base

import com.example.domain.remote_protocol.MessageDto
import com.example.domain.remote_protocol.User
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<MessageDto>
    val me: User
}