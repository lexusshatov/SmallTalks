package com.example.domain.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Message::class], version = 3)
@TypeConverters(UserConverter::class)
abstract class MessageDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao
}