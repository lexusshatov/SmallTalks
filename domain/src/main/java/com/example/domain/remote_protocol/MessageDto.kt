package com.example.domain.remote_protocol

data class MessageDto(val from: User, val message: String) : Payload