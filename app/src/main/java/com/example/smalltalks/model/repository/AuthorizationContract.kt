package com.example.smalltalks.model.repository

import androidx.lifecycle.LiveData

interface AuthorizationContract {
    val connect: LiveData<Boolean>
    fun connect(userName: String)
}