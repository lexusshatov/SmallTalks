package com.example.smalltalks.model

data class MessageDto(val from: User, val message: String) : Payload