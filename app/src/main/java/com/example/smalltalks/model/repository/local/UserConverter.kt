package com.example.smalltalks.model.repository.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.smalltalks.model.remote_protocol.User
import com.google.gson.Gson
import javax.inject.Inject

@ProvidedTypeConverter
class UserConverter @Inject constructor(
    private val gson: Gson
) {

    @TypeConverter
    fun toDatabase(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun fromDatabase(user: String): User {
        return gson.fromJson(user, User::class.java)
    }
}