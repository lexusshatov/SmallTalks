package com.example.smalltalks.model.repository.base

import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.User
import kotlinx.coroutines.flow.Flow

interface UserContract {
    val messages: Flow<MessageDto>
    val me: User
}