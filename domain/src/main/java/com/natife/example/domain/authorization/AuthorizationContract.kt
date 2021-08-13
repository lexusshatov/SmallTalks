package com.natife.example.domain.authorization

import com.natife.example.domain.repository.remote.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {

    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
}