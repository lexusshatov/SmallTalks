package com.example.data.repository.local

import android.content.Context
import androidx.core.content.edit
import com.example.data.repository.local.data.MessageDao
import com.example.data.repository.local.data.MessageDb
import com.google.gson.Gson
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.LocalData
import com.natife.example.domain.repository.local.Message
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        val messageDb = MessageDb(
            from = message.from,
            to = message.to,
            message = message.message
        )
        dao.addMessage(messageDb)
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

    override fun getDialog(receiver: User): Flow<List<Message>> {
        val receiverJson = gson.toJson(receiver)
        return dao.getDialog(receiverJson)
            .map { list ->
                list.map {
                    Message(
                        from = it.from,
                        to = it.to,
                        message = it.message
                    )
                }
            }
    }


    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"
    }
}
