package com.natife.example.domain.decorator

import com.example.core.authorization.AuthorizationContract
import com.example.core.repository.RemoteData
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val socketRepository: RemoteData
) : AuthorizationContract {

    override val connectState = socketRepository.connectState

    override suspend fun connect(userName: String) =
        socketRepository.connect(userName)
}