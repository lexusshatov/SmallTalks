package com.example.smalltalks.model.di.decorator

import com.example.smalltalks.model.repository.base.AuthorizationContract
import com.example.smalltalks.model.repository.decorator.DataRepository
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
    fun bindDecorator(repositoryDecorator: RepositoryDecorator) : DataRepository

    @Binds
    @ActivityRetainedScoped
    fun bindAuthDecorator(repositoryDecorator: RepositoryDecorator) : AuthorizationContract
}