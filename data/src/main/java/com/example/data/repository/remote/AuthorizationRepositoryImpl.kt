package com.example.data.repository.remote

import com.natife.example.domain.ConnectState
import com.natife.example.domain.authorization.AuthorizationRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val socketHolder: SocketHolder
) : AuthorizationRepository {

    private val mutableConnectState = MutableStateFlow<ConnectState>(ConnectState.Nothing)
    override val connectState: StateFlow<ConnectState>
        get() = mutableConnectState

    override suspend fun connect(userName: String) {
        mutableConnectState.value = ConnectState.Connect(userName)
        runCatching {
            socketHolder.connect(userName)
        }
            .onSuccess { mutableConnectState.value = ConnectState.Success }
            .onFailure {
                mutableConnectState.value = ConnectState.Error(it.message ?: "Unknown error")
                runCatching { socketHolder.disconnect() }
            }
    }

    override suspend fun disconnect() {
        withContext(NonCancellable) {
            socketHolder.disconnect()
        }
    }
}