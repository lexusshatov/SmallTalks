package com.example.core.base.authorization

import com.example.core.base.repository.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {
    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
}