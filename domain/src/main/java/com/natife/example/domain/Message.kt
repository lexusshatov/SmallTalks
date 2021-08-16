package com.natife.example.domain

import com.natife.example.domain.dto.User

data class Message(
    val from: User,
    val to: User,
    val message: String
)
