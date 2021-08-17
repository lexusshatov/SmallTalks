package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.AuthorizationRepositoryImpl
import com.example.data.repository.MessageRepositoryImpl
import com.example.data.repository.remote.UsersRepositoryImpl
import com.natife.example.domain.repository.AuthorizationRepository
import com.natife.example.domain.repository.MessageRepository
import com.natife.example.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteRepositoryBindsModule {

    @Binds
    fun bindAuthorizationRepository(authorizationRepositoryImpl: AuthorizationRepositoryImpl)
            : AuthorizationRepository

    @Binds
    fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}