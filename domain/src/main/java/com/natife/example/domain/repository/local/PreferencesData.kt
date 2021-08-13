package com.natife.example.domain.repository.local

interface PreferencesData {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}