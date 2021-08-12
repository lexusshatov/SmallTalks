package com.example.smalltalks.di.repository.remote

import com.example.core.repository.RemoteData
import com.natife.example.domain.repository.SocketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SocketRepositoryBindsModule {

    @Binds
    @ActivityRetainedScoped
    fun getAuthorizationContract(repository: SocketRepository) : RemoteData
}