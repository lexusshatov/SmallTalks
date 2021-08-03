package com.example.smalltalks.model.repository.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.smalltalks.view.chat.MessageItem
import com.google.gson.Gson
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun toDatabase(messageItem: MessageItem): String =
        gson.toJson(messageItem)

    @TypeConverter
    fun fromDatabase(messageItem: String): MessageItem =
        gson.fromJson(messageItem, MessageItem::class.java)
}