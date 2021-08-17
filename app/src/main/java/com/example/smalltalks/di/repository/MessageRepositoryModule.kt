package com.example.smalltalks.di.repository

import com.example.data.repository.MessageDecorator
import com.natife.example.domain.repository.MessageRepository
import com.natife.example.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MessageRepositoryModule {

    @Provides
    fun provideMessageDecorator(
        @Repository messageRepository: MessageRepository,
        usersRepository: UsersRepository
    ) = MessageDecorator(messageRepository, usersRepository)
}