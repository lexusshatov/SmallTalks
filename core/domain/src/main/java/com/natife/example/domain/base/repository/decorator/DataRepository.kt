package com.natife.example.domain.base.repository.decorator

import com.natife.example.domain.base.authorization.AuthorizationContract
import com.natife.example.domain.base.chat.ChatContract
import com.natife.example.domain.base.repository.local.PreferencesData
import com.natife.example.domain.base.userlist.UsersContract

interface DataRepository : AuthorizationContract, ChatContract, UsersContract, PreferencesData