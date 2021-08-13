package com.natife.example.domain.repository.local

import com.natife.example.domain.dto.User

data class Message(
    val from: User,
    val to: User,
    val message: String
)
