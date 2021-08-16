package com.example.smalltalks.di.repository.decorator

import com.example.data.repository.MessageDecorator
import com.natife.example.domain.chat.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatDecoratorBindsModule {

    @Binds
    @Decorator
    fun bindChatDecorator(messageDecorator: MessageDecorator): MessageRepository
}