package com.example.domain.di.decorator

import com.example.domain.repository.base.authorization.AuthorizationContract
import com.example.domain.repository.base.chat.ChatContract
import com.example.domain.repository.base.repository.PreferencesData
import com.example.domain.repository.base.userlist.UsersContract
import com.example.domain.repository.decorator.RepositoryDecorator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DecoratorBindsModule {


    @Binds
    @ActivityRetainedScoped
    fun bindAuthDecorator(repositoryDecorator: RepositoryDecorator): AuthorizationContract

    @Binds
    @ActivityRetainedScoped
    fun bindChatDecorator(repositoryDecorator: RepositoryDecorator): ChatContract

    @Binds
    @ActivityRetainedScoped
    fun bindUserListDecorator(repositoryDecorator: RepositoryDecorator): UsersContract

    @Binds
    @ActivityRetainedScoped
    fun bindPreferencesDecorator(repositoryDecorator: RepositoryDecorator): PreferencesData
}