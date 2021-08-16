package com.natife.example.domain.remote

import kotlinx.coroutines.flow.StateFlow

interface AuthorizationRepository {

    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
    suspend fun disconnect()
}