package com.example.core.repository

import com.example.core.authorization.AuthorizationContract
import com.example.core.chat.RemoteChatContract
import com.example.core.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract