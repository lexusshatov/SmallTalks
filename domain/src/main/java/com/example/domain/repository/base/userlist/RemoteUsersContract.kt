package com.example.domain.repository.base.userlist

import androidx.lifecycle.LiveData
import com.example.domain.repository.base.UserContract

interface RemoteUsersContract: UserContract {

    val users: LiveData<List<com.example.domain.remote_protocol.User>>
    suspend fun disconnect()
}