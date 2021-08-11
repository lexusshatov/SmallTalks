package com.example.core.base.repository

import com.example.core.base.authorization.AuthorizationContract
import com.example.core.base.chat.RemoteChatContract
import com.example.core.base.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract