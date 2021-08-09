package com.example.smalltalks.model.di.decorator

import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.model.repository.base.chat.ChatContract
import com.example.smalltalks.model.repository.base.repository.PreferencesData
import com.example.smalltalks.model.repository.base.userlist.UsersContract
import com.example.smalltalks.model.repository.decorator.RepositoryDecorator
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