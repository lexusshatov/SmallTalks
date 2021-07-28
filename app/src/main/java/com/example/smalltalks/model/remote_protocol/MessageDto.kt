package com.example.smalltalks.model.remote_protocol

data class MessageDto(val from: User, val message: String) : Payload