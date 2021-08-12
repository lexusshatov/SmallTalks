package com.example.smalltalks.di.decorator

import com.example.core.authorization.AuthorizationContract
import com.example.core.chat.ChatContract
import com.example.core.repository.local.PreferencesData
import com.example.core.userlist.UsersContract
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