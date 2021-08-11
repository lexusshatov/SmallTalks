package com.natife.example.domain.dto

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload