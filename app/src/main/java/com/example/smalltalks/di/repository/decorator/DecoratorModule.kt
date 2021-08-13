package com.example.smalltalks.di.repository.decorator

import com.example.data.repository.ChatDecorator
import com.example.data.repository.remote.RemoteRepository
import com.example.smalltalks.di.repository.remote.Repository
import com.natife.example.domain.repository.local.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DecoratorModule {

    @Provides
    @Singleton
    fun provideDecorator(localRepository: LocalRepository, remoteRepository: RemoteRepository) =
        ChatDecorator(localRepository, remoteRepository)
}