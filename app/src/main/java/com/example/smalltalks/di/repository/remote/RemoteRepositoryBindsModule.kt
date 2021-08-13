package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.RemoteRepository
import com.natife.example.domain.authorization.AuthorizationRepository
import com.natife.example.domain.chat.ChatRepository
import com.natife.example.domain.userlist.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RemoteRepositoryBindsModule {

    @Binds
    @ActivityRetainedScoped
    fun authorizationRepository(repository: RemoteRepository): AuthorizationRepository

    @Binds
    @ActivityRetainedScoped
    fun chatRepository(repository: RemoteRepository): ChatRepository

    @Binds
    @ActivityRetainedScoped
    fun usersRepository(repository: RemoteRepository): UsersRepository
}