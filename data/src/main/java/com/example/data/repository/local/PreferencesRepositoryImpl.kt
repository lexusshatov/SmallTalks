package com.example.data.repository.local

import android.content.Context
import androidx.core.content.edit
import com.natife.example.domain.repository.local.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesRepository {

    private val preferences = context.getSharedPreferences(
        LocalRepositoryImpl.USER_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun deleteUserName() {
        preferences.edit {
            remove(LocalRepositoryImpl.USER_NAME)
            apply()
        }
    }

    override fun getUserName(): String? = preferences.getString(LocalRepositoryImpl.USER_NAME, null)

    override fun saveUserName(userName: String) {
        preferences.edit {
            putString(LocalRepositoryImpl.USER_NAME, userName)
            apply()
        }
    }
}