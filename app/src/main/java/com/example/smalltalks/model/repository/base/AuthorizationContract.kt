package com.example.smalltalks.model.repository.base

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.repository.remote.ConnectState

interface AuthorizationContract {
    val connect: LiveData<ConnectState>
    fun connect(userName: String)
    fun disconnect()
}