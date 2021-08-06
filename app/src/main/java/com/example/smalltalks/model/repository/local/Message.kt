package com.example.smalltalks.model.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smalltalks.model.remote_protocol.User
//TODO database packet
@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val from: User,
    val to: User,
    val message: String
)