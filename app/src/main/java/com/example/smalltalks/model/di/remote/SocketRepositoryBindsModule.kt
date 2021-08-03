package com.example.smalltalks.model.di.remote

import com.example.smalltalks.model.repository.remote.AuthorizationContract
import com.example.smalltalks.model.repository.remote.ChatContract
import com.example.smalltalks.model.repository.remote.SocketRepository
import com.example.smalltalks.model.repository.remote.UserListContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SocketRepositoryBindsModule {

    @Socket
    @Binds
    fun getAuthorizationContract(repository: SocketRepository) : AuthorizationContract

    @Socket
    @Binds
    fun getChatContract(repository: SocketRepository) : ChatContract

    @Socket
    @Binds
    fun getUserListContract(repository: SocketRepository) : UserListContract
}