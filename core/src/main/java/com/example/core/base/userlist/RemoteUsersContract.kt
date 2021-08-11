package com.example.core.base.userlist

import androidx.lifecycle.LiveData
import com.example.core.dto.User
import com.example.core.base.UserContract

interface RemoteUsersContract: UserContract {

    val users: LiveData<List<User>>
    suspend fun disconnect()
}