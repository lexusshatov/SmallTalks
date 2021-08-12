package com.example.smalltalks.di.decorator

import com.example.core.authorization.AuthorizationContract
import com.example.core.chat.ChatContract
import com.example.core.repository.local.PreferencesData
import com.example.core.userlist.UsersContract
import com.natife.example.domain.decorator.AuthRepository
import com.natife.example.domain.decorator.ChatDecorator
import com.natife.example.domain.decorator.PreferencesRepository
import com.natife.example.domain.decorator.UsersRepository
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
    fun bindAuthDecorator(authRepository: AuthRepository): AuthorizationContract

    @Binds
    @ActivityRetainedScoped
    fun bindChatDecorator(chatDecorator: ChatDecorator): ChatContract

    @Binds
    @ActivityRetainedScoped
    fun bindUsersDecorator(usersRepository: UsersRepository): UsersContract

    @Binds
    @ActivityRetainedScoped
    fun bindPreferencesDecorator(preferencesRepository: PreferencesRepository): PreferencesData
}