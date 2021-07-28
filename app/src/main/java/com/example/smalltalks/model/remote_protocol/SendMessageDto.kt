package com.example.smalltalks.model.remote_protocol

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload