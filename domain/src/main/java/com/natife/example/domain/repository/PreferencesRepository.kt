package com.natife.example.domain.repository

interface PreferencesRepository {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}