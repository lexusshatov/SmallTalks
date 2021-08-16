package com.natife.example.domain.remote

sealed class ConnectState {

    object Nothing : ConnectState()
    data class Connect(val userName: String) : ConnectState()
    object Success : ConnectState()
    data class Error(val message: String) : ConnectState()
}