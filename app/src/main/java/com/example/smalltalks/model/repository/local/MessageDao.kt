package com.example.smalltalks.model.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM Message WHERE (`from`= :receiver OR `to`= :receiver)")
    fun getDialog(receiver: String): LiveData<List<Message>>

    @Insert
    suspend fun addMessage(message: Message)
}