package com.natife.example.domain.repository.local

interface PreferencesRepository {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}