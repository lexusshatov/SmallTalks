package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.RemoteRepository
import com.natife.example.domain.authorization.AuthorizationRepository
import com.natife.example.domain.chat.ChatRepository
import com.natife.example.domain.chat.MessageRepository
import com.natife.example.domain.userlist.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteRepositoryBindsModule {

    @Binds
    @Singleton
    fun authorizationRepository(repository: RemoteRepository): AuthorizationRepository

    @Binds
    @Singleton
    @Repository
    fun chatRepository(repository: RemoteRepository): ChatRepository

    @Binds
    @Singleton
    fun messageRepository(repository: RemoteRepository): MessageRepository

    @Binds
    @Singleton
    fun usersRepository(repository: RemoteRepository): UsersRepository
}