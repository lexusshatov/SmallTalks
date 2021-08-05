package com.example.smalltalks.model.di.decorator

import com.example.smalltalks.model.repository.decorator.RepositoryDecorator
import com.example.smalltalks.model.repository.local.LocalData
import com.example.smalltalks.model.repository.remote.RemoteData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class DecoratorModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDecorator(localRepository: LocalData, remoteRepository: RemoteData) =
        RepositoryDecorator(localRepository, remoteRepository)
}