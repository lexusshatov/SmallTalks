package com.natife.example.domain.base.authorization

import com.natife.example.domain.base.repository.ConnectState
import kotlinx.coroutines.flow.StateFlow

interface AuthorizationContract {

    val connectState: StateFlow<ConnectState>
    suspend fun connect(userName: String)
}