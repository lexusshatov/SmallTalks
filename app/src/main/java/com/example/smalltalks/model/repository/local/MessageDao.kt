package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM Message WHERE (`from`= :user1 AND `to`= :user2) OR (`to`= :user1 AND `from`= :user2)")
    fun getDialog(user1: String, user2: String): LiveData<List<Message>>

    @Insert
    suspend fun addMessage(message: Message)
}