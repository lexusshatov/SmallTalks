package com.natife.example.domain.dto

data class MessageDto(val from: User, val message: String) : Payload