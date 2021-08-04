package com.example.smalltalks.model.repository.base

import com.example.smalltalks.model.remote_protocol.User

interface UserContract {
    val me: User
}