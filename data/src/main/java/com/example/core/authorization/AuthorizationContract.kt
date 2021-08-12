package com.example.core.authorization

import com.example.core.repository.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {

    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
}