package com.example.smalltalks.model.repository.remote

sealed class ConnectState {

    data class Connect(val userName: String) : ConnectState()
    object Success : ConnectState()
    data class Error(val message: String) : ConnectState()
}