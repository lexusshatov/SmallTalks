package com.natife.example.domain.authorization

import com.natife.example.domain.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationRepository {

    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
    suspend fun disconnect()
}