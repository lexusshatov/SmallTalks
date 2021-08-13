package com.example.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MessageDb::class], version = 4, exportSchema = false)
@TypeConverters(UserConverter::class)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
}