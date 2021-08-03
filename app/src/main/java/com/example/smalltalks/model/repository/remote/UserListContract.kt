package com.example.smalltalks.model.repository.remote

import androidx.lifecycle.LiveData
import com.example.smalltalks.model.remote_protocol.User

interface UserListContract: UserContract {
    val users: LiveData<List<User>>
}