package com.example.domain.repository.base.authorization

import com.example.domain.repository.remote.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {
    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
}