package com.example.core.di

import com.example.core.base.authorization.AuthorizationContract
import com.example.core.base.chat.ChatContract
import com.example.core.base.repository.local.PreferencesData
import com.example.core.base.userlist.UsersContract
import javax.inject.Inject

class DecoratorInjector {

    @Inject
    lateinit var authDecorator: AuthorizationContract

    @Inject
    lateinit var chatDecorator: ChatContract

    @Inject
    lateinit var usersDecorator: UsersContract

    @Inject
    lateinit var preferencesDecorator: PreferencesData
}