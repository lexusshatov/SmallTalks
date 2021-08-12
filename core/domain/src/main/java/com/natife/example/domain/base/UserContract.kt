package com.natife.example.domain.base

import com.natife.example.domain.base.dto.MessageDto
import com.natife.example.domain.base.dto.User
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<MessageDto>
    val me: User
}