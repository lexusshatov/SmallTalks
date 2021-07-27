package com.example.smalltalks.model

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload