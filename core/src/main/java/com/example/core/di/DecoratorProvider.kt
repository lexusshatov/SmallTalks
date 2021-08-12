package com.example.core.di

import com.example.core.base.authorization.AuthorizationContract
import com.example.core.base.chat.ChatContract
import com.example.core.base.repository.local.PreferencesData
import com.example.core.base.userlist.UsersContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class DecoratorProvider {
/*
    @Inject
    @Internal
    lateinit var authDecorator: AuthorizationContract

    @Inject
    @Internal
    lateinit var chatDecorator: ChatContract

    @Inject
    @Internal
    lateinit var usersDecorator: UsersContract

    @Inject
    @Internal
    lateinit var preferencesData: PreferencesData
*/

    @Provides
    @ActivityRetainedScoped
    fun provideAuthDecorator(@Internal authDecorator: AuthorizationContract) = authDecorator

    @Provides
    @ActivityRetainedScoped
    fun provideChatDecorator(@Internal chatDecorator: ChatContract) = chatDecorator

    @Provides
    @ActivityRetainedScoped
    fun provideUsersDecorator(@Internal usersDecorator: UsersContract) = usersDecorator

    @Provides
    @ActivityRetainedScoped
    fun providePreferencesDecorator(@Internal preferencesData: PreferencesData) = preferencesData
}