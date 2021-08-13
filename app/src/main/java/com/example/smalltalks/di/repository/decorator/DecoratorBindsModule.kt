package com.example.smalltalks.di.repository.decorator

import com.example.data.repository.ChatDecorator
import com.natife.example.domain.chat.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DecoratorBindsModule {

    @Binds
    @Singleton
    @Decorator
    fun chatDecorator(decorator: ChatDecorator): ChatRepository
}