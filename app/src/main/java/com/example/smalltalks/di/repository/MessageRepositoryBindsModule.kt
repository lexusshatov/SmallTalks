package com.example.smalltalks.di.repository

import com.example.data.repository.MessageRepositoryImpl
import com.natife.example.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MessageRepositoryBindsModule {

    @Binds
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository
}