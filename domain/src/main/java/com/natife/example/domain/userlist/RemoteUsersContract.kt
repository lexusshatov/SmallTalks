package com.natife.example.domain.userlist

import com.natife.example.domain.UserContract
import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface RemoteUsersContract: UserContract {

    val users: Flow<List<User>>
    suspend fun disconnect()
}