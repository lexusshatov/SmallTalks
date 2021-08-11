package com.example.domain.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.remote_protocol.User

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val from: User,
    val to: User,
    val message: String
)