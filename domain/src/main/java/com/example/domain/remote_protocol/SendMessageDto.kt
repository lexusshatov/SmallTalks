package com.example.domain.remote_protocol

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload