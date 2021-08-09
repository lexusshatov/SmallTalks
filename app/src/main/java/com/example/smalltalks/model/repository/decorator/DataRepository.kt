package com.example.smalltalks.model.repository.decorator

import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.model.repository.base.chat.ChatContract
import com.example.smalltalks.model.repository.base.userlist.UsersContract

interface DataRepository: AuthorizationContract, ChatContract, UsersContract