package com.example.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class DecoratorProvider {
    private val injector = DecoratorInjector()

    @Provides
    @ActivityRetainedScoped
    fun provideAuthDecorator() = injector.authDecorator

    @Provides
    @ActivityRetainedScoped
    fun provideChatDecorator() = injector.chatDecorator

    @Provides
    @ActivityRetainedScoped
    fun provideUsersDecorator() = injector.usersDecorator

    @Provides
    @ActivityRetainedScoped
    fun providePreferencesDecorator() = injector.preferencesDecorator
}