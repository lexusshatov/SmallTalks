package com.example.smalltalks.model.repository.base.repository

import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.model.repository.base.chat.RemoteChatContract
import com.example.smalltalks.model.repository.base.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract