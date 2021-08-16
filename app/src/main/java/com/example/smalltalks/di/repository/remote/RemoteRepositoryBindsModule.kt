package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.AuthorizationRepositoryImpl
import com.example.data.repository.remote.MessageRepositoryImpl
import com.example.data.repository.remote.UsersRepositoryImpl
import com.natife.example.domain.authorization.AuthorizationRepository
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
    fun bindAuthorizationRepository(authorizationRepositoryImpl: AuthorizationRepositoryImpl)
            : AuthorizationRepository

    @Binds
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    @Binds
    fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}