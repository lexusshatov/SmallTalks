package com.example.core

import com.example.core.dto.MessageDto
import com.example.core.dto.User
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<MessageDto>
    val me: User
}