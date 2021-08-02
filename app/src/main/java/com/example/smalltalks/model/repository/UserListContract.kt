package com.example.smalltalks.model.repository

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.repository.UserContract

interface UserListContract: UserContract {
    val users: LiveData<List<User>>
}