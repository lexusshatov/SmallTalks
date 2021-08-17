package com.example.smalltalks.di.repository

import com.example.data.repository.MessageDecorator
import com.example.data.repository.MessageRepositoryImpl
import com.example.data.repository.local.PreferencesRepositoryImpl
import com.example.data.repository.remote.AuthorizationRepositoryImpl
import com.example.data.repository.remote.UsersRepositoryImpl
import com.natife.example.domain.repository.AuthorizationRepository
import com.natife.example.domain.repository.MessageRepository
import com.natife.example.domain.repository.PreferencesRepository
import com.natife.example.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindsModule {

    @Binds
    @Singleton
    fun bindPreferencesRepository(preferencesRepositoryImpl: PreferencesRepositoryImpl)
            : PreferencesRepository

    @Binds
    @Repository
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    @Binds
    fun bindAuthorizationRepository(authorizationRepositoryImpl: AuthorizationRepositoryImpl)
            : AuthorizationRepository

    @Binds
    fun bindMessageDecorator(messageDecorator: MessageDecorator): MessageRepository

    @Binds
    fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}