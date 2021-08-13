package com.natife.example.domain.repository.remote

sealed class ConnectState {

    object Nothing : ConnectState()
    data class Connect(val userName: String) : ConnectState()
    object Success : ConnectState()
    data class Error(val message: String) : ConnectState()
}