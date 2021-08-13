package com.natife.example.domain

import com.natife.example.domain.dto.User

interface UserRepository {

    val me: User
}