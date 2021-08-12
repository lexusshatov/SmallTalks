package com.natife.example.domain.base.dto

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload