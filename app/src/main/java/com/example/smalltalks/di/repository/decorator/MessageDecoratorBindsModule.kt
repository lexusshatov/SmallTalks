package com.example.smalltalks.di.repository.decorator

import com.example.data.repository.MessageDecorator
import com.natife.example.domain.remote.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MessageDecoratorBindsModule {

    @Binds
    @Decorator
    fun bindMessageDecorator(messageDecorator: MessageDecorator): MessageRepository
}