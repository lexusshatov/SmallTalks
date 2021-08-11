package com.example.domain.repository.decorator

import com.example.domain.repository.base.authorization.AuthorizationContract
import com.example.domain.repository.base.chat.ChatContract
import com.example.domain.repository.base.repository.PreferencesData
import com.example.domain.repository.base.userlist.UsersContract

interface DataRepository: AuthorizationContract, ChatContract, UsersContract, PreferencesData