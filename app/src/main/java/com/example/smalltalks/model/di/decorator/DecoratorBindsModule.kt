package com.example.smalltalks.model.di.decorator

import com.example.smalltalks.model.repository.remote.RemoteData
import com.example.smalltalks.model.repository.decorator.RepositoryDecorator
import com.example.smalltalks.model.repository.AuthorizationContract
import com.example.smalltalks.model.repository.local.LocalData
import com.example.smalltalks.model.repository.UserListContract
import com.example.smalltalks.model.repository.decorator.DataRepository
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
    fun bindDecorator(repositoryDecorator: RepositoryDecorator) : DataRepository

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindDecoratorRemote(repositoryDecorator: RepositoryDecorator) : RemoteData

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindDecoratorLocal(repositoryDecorator: RepositoryDecorator) : LocalData

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindAuthDecorator(@Decorator decorator: RemoteData) : AuthorizationContract

    @Decorator
    @Binds
    @ActivityRetainedScoped
    fun bindUserListDecorator(@Decorator decorator: RemoteData) : UserListContract
}