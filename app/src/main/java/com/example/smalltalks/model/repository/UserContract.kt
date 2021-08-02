package com.example.smalltalks.model.repository

import com.example.smalltalks.model.remote_protocol.User

interface UserContract {
    val me: User
}