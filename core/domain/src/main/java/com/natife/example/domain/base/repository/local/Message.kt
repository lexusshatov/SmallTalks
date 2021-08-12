package com.natife.example.domain.base.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.natife.example.domain.base.dto.User

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val from: User,
    val to: User,
    val message: String
)