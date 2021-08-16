package com.example.data.repository.local

import android.content.Context
import androidx.core.content.edit
import com.natife.example.domain.local.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesRepository {

    private val preferences = context.getSharedPreferences(
        DialogRepositoryImpl.USER_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun deleteUserName() {
        preferences.edit {
            remove(DialogRepositoryImpl.USER_NAME)
        }
    }

    override fun getUserName(): String? =
        preferences.getString(DialogRepositoryImpl.USER_NAME, null)

    override fun saveUserName(userName: String) {
        preferences.edit {
            putString(DialogRepositoryImpl.USER_NAME, userName)
        }
    }
}