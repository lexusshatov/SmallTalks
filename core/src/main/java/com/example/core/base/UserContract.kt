package com.example.core.base

import com.natife.example.domain.dto.MessageDto
import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<MessageDto>
    val me: User
}