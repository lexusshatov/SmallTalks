package com.example.core.base.repository.decorator

import com.example.core.base.authorization.AuthorizationContract
import com.example.core.base.chat.ChatContract
import com.example.core.base.repository.local.PreferencesData
import com.example.core.base.userlist.UsersContract

interface DataRepository: AuthorizationContract, ChatContract, UsersContract, PreferencesData