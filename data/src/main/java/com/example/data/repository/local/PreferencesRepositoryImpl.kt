package com.example.data.repository.local

import android.content.Context
import androidx.core.content.edit
import com.natife.example.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesRepository {

    private val preferences = context.getSharedPreferences(
        USER_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun deleteUserName() {
        preferences.edit {
            remove(USER_NAME)
        }
    }

    override fun getUserName(): String? =
        preferences.getString(USER_NAME, null)

    override fun saveUserName(userName: String) {
        preferences.edit {
            putString(USER_NAME, userName)
        }
    }

    companion object {
        const val USER_NAME = "User_name"
        const val USER_PREFERENCES = "User_preferences"
    }
}