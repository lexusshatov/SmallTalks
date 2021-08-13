package com.natife.example.domain

import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.Message
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<Message>
    val me: User
}