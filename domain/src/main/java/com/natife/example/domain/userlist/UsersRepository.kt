package com.natife.example.domain.userlist

import com.natife.example.domain.UserRepository
import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository : UserRepository {

    val users: Flow<List<User>>
    suspend fun disconnect()
}