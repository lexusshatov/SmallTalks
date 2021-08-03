package com.example.smalltalks.model.di

import com.example.smalltalks.model.repository.DataRepository
import com.example.smalltalks.model.repository.RepositoryDecorator
import com.example.smalltalks.model.repository.remote.AuthorizationContract
import com.example.smalltalks.model.repository.remote.ChatContract
import com.example.smalltalks.model.repository.remote.LocalChatContract
import com.example.smalltalks.model.repository.remote.UserListContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DecoratorBindsModule {

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindDecorator(decorator: RepositoryDecorator) : DataRepository

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindDecoratorLocal(decorator: RepositoryDecorator) : LocalChatContract

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindAuthDecorator(@Decorator decorator: DataRepository) : AuthorizationContract

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindUserListDecorator(@Decorator decorator: DataRepository) : UserListContract
}