package com.natife.example.domain.base.repository.local

interface PreferencesData {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}