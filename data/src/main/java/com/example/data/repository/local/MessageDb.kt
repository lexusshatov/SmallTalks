package com.example.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.natife.example.domain.dto.User

@Entity
data class MessageDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val from: User,
    val to: User,
    val message: String
)