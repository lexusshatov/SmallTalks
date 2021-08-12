package com.example.core.di.decorator

import com.natife.example.domain.base.authorization.AuthorizationContract
import com.natife.example.domain.base.chat.ChatContract
import com.natife.example.domain.base.repository.local.PreferencesData
import com.natife.example.domain.base.userlist.UsersContract
import com.natife.example.domain.decorator.RepositoryDecorator
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