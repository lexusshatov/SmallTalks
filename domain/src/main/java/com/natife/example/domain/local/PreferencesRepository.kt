package com.natife.example.domain.local

interface PreferencesRepository {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}