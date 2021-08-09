package com.example.smalltalks.model.repository.base.userlist

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.base.UserContract

interface RemoteUsersContract: UserContract {

    val users: LiveData<List<User>>
    suspend fun disconnect()
}