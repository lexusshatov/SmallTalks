package com.example.smalltalks.model.di.remote

import com.example.smalltalks.model.repository.AuthorizationContract
import com.example.smalltalks.model.repository.ChatContract
import com.example.smalltalks.model.repository.remote.SocketRemote
import com.example.smalltalks.model.repository.UserListContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SocketRepositoryBindsModule {

    @Socket
    @Binds
    fun getAuthorizationContract(repository: SocketRemote) : AuthorizationContract

    @Socket
    @Binds
    fun getChatContract(repository: SocketRemote) : ChatContract

    @Socket
    @Binds
    fun getUserListContract(repository: SocketRemote) : UserListContract
}