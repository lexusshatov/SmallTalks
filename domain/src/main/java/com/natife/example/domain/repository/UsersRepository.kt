package com.natife.example.domain.repository

import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    val me: User
    val users: Flow<List<User>>
}