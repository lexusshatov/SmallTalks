package com.example.smalltalks.model.remote_protocol

data class UsersReceivedDto(val users: List<User>) : Payload

data class User(val id: String, val name: String)