package com.example.smalltalks.model.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.smalltalks.view.chat.MessageItem

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val messageItem: MessageItem
)