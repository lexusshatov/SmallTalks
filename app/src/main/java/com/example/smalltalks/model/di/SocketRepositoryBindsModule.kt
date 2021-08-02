package com.example.smalltalks.model.di

import com.example.smalltalks.model.repository.AuthorizationContract
import com.example.smalltalks.model.repository.ChatContract
import com.example.smalltalks.model.repository.SocketRepository
import com.example.smalltalks.model.repository.UserListContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SocketRepositoryBindsModule {

    @Binds
    fun getAuthorizationContract(repository: SocketRepository) : AuthorizationContract

    @Binds
    fun getChatContract(repository: SocketRepository) : ChatContract

    @Binds
    fun getUserListContract(repository: SocketRepository) : UserListContract
}