package com.example.domain.remote_protocol

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class UsersReceivedDto(val users: List<User>) : Payload

@Parcelize
data class User(val id: String, val name: String): Parcelable