package com.example.domain.repository.base.repository

interface PreferencesData {

    fun deleteUserName()
    fun getUserName(): String?
    fun saveUserName(userName: String)
}