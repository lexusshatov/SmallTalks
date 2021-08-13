package com.example.smalltalks.di.decorator

import com.example.data.decorator.AuthRepository
import com.example.data.decorator.ChatDecorator
import com.example.data.decorator.PreferencesRepository
import com.example.data.decorator.UsersRepository
import com.natife.example.domain.authorization.AuthorizationContract
import com.natife.example.domain.chat.ChatContract
import com.natife.example.domain.repository.local.PreferencesData
import com.natife.example.domain.userlist.UsersContract
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