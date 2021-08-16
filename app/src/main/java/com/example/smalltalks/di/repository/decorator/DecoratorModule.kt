package com.example.smalltalks.di.repository.decorator

import com.example.data.repository.ChatDecorator
import com.example.data.repository.remote.RemoteRepository
import com.natife.example.domain.repository.local.DialogRepository
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
    fun provideDecorator(dialogRepository: DialogRepository, remoteRepository: RemoteRepository) =
        ChatDecorator(dialogRepository, remoteRepository)
}