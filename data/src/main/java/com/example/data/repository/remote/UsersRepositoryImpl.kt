package com.example.data.repository.remote

import com.natife.example.domain.dto.User
import com.natife.example.domain.userlist.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val socketHolder: SocketHolder
) : UsersRepository {

    override val me: User
        get() = socketHolder.me
    override val users: Flow<List<User>>
        get() = socketHolder.users
}