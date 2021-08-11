package com.example.domain.di.remote

import com.example.domain.repository.SocketRepository
import com.example.domain.repository.base.repository.RemoteData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SocketRepositoryBindsModule {

    @Binds
    fun getAuthorizationContract(repository: SocketRepository) : RemoteData
}