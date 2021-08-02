package com.example.smalltalks.view.chat

import com.example.smalltalks.model.remote_protocol.MessageDto

data class MessageItem(
    val messageDto: MessageDto,
    val fromMe: Boolean
)
