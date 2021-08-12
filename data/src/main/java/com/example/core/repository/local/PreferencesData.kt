package com.example.core.repository.local

interface PreferencesData {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}