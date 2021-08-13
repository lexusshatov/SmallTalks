package com.example.data.repository.local.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageDb WHERE (`from`= :receiver OR `to`= :receiver)")
    fun getDialog(receiver: String): Flow<List<MessageDb>>

    @Insert
    suspend fun addMessage(message: MessageDb)
}