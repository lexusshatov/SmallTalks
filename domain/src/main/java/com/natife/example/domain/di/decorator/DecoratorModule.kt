package com.natife.example.domain.di.decorator

import com.example.core.base.repository.RemoteData
import com.example.core.base.repository.local.LocalData
import com.natife.example.domain.decorator.RepositoryDecorator
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