package com.example.data.repository

import com.natife.example.domain.authorization.AuthorizationContract
import com.natife.example.domain.repository.remote.RemoteData
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val socketRepository: RemoteData
) : AuthorizationContract {

    override val connectState = socketRepository.connectState

    override suspend fun connect(userName: String) =
        socketRepository.connect(userName)
}