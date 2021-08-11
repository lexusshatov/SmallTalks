package com.example.domain.repository.base.repository

import com.example.domain.repository.base.authorization.AuthorizationContract
import com.example.domain.repository.base.chat.RemoteChatContract
import com.example.domain.repository.base.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract