package com.example.smalltalks.model.remote_protocol

import java.io.Serializable

data class UsersReceivedDto(val users: List<User>) : Payload

data class User(val id: String, val name: String): Serializable