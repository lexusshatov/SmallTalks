package com.example.core.base.repository

sealed class ConnectState {

    object Nothing : ConnectState()
    data class Connect(val userName: String) : ConnectState()
    object Success : ConnectState()
    data class Error(val message: String) : ConnectState()
}