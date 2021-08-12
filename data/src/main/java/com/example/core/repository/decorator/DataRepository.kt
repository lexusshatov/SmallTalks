package com.example.core.repository.decorator

import com.example.core.authorization.AuthorizationContract
import com.example.core.chat.ChatContract
import com.example.core.repository.local.PreferencesData
import com.example.core.userlist.UsersContract

interface DataRepository : AuthorizationContract, ChatContract, UsersContract, PreferencesData