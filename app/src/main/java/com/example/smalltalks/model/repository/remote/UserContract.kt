package com.example.smalltalks.model.repository.remote

import com.example.smalltalks.model.remote_protocol.User

interface UserContract {
    val me: User
}