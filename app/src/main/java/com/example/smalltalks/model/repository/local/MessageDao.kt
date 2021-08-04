package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM Message")
    fun getAll(): LiveData<List<Message>>

    @Insert
    fun addMessage(message: Message)
}