package com.natife.example.domain.base.userlist

import androidx.lifecycle.LiveData
import com.natife.example.domain.base.dto.User
import com.natife.example.domain.base.UserContract

interface RemoteUsersContract: UserContract {

    val users: LiveData<List<User>>
    suspend fun disconnect()
}