package com.example.smalltalks.model.repository.base

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.repository.remote.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {
    val connectState: StateFlow<ConnectState>
    fun connect(userName: String)
    fun disconnect()
}