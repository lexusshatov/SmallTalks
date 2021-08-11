package com.example.core.base.repository.local

interface PreferencesData {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}