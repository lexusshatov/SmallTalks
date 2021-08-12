package com.natife.example.domain.base.dto

data class MessageDto(val from: User, val message: String) : Payload