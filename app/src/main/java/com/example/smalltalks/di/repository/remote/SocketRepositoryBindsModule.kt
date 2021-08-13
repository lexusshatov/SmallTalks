package com.example.smalltalks.di.repository.remote

import com.example.data.repository.SocketRepository
import com.natife.example.domain.repository.remote.RemoteData
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
    fun getAuthorizationContract(repository: SocketRepository): RemoteData
}