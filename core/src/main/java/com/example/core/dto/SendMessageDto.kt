package com.example.core.dto

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload