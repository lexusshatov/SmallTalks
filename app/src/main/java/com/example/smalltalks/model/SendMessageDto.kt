package com.example.smalltalks.model

import com.example.smalltalks.model.Payload

data class SendMessageDto(val id: String, val receiver: String, val message: String) : Payload