package com.natife.example.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.repository.local.Message
import com.example.core.repository.local.MessageDao

@Database(entities = [Message::class], version = 3, exportSchema = false)
@TypeConverters(UserConverter::class)
abstract class MessageDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao
}