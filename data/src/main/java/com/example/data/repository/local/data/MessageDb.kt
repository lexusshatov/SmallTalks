package com.example.data.repository.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.natife.example.domain.dto.User
import com.natife.example.domain.Message

@Entity
data class MessageDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val from: User,
    val to: User,
    val message: String
)

fun Message.toDatabase() = MessageDb(
    from = from,
    to = to,
    message = message
)

fun MessageDb.toMessage() = Message(
    from = from,
    to = to,
    message = message
)