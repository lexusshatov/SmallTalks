package com.example.domain.di.decorator

import com.example.domain.repository.base.repository.LocalData
import com.example.domain.repository.base.repository.RemoteData
import com.example.domain.repository.decorator.RepositoryDecorator
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