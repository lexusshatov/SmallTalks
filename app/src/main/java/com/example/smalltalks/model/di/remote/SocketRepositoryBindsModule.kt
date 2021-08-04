package com.example.smalltalks.model.di.remote

import com.example.smalltalks.model.repository.remote.RemoteData
import com.example.smalltalks.model.repository.remote.SocketRepository
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