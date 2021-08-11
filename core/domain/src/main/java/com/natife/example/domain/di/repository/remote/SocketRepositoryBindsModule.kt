package com.natife.example.domain.di.repository.remote

import com.example.core.base.repository.RemoteData
import com.natife.example.domain.repository.SocketRepository
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