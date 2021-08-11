package com.example.core.base.userlist

import androidx.lifecycle.LiveData
import com.example.core.base.UserContract
import com.natife.example.domain.dto.User

interface RemoteUsersContract: UserContract {

    val users: LiveData<List<User>>
    suspend fun disconnect()
}