package com.example.smalltalks.model.repository.base.authorization

import com.example.smalltalks.model.repository.remote.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {
    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
    suspend fun disconnect()
}