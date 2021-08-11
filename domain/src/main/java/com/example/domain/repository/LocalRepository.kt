package com.example.domain.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.example.domain.repository.base.repository.LocalData
import com.example.domain.repository.local.Message
import com.example.domain.repository.local.MessageDao
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val gson: Gson,
    private val dao: MessageDao,
    @ApplicationContext private val context: Context
) : LocalData {
    private val preferences = context.getSharedPreferences(
        USER_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override suspend fun saveMessage(message: Message) {
        dao.addMessage(message)
    }

    override fun deleteUserName() {
        preferences.edit {
            remove(USER_NAME)
            apply()
        }
    }

    override fun getUserName(): String? = preferences.getString(USER_NAME, null)

    override fun saveUserName(userName: String) {
        preferences.edit {
            putString(USER_NAME, userName)
            apply()
        }
    }

    override fun getDialog(receiver: com.example.domain.remote_protocol.User): LiveData<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
    }

    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"
    }
}
