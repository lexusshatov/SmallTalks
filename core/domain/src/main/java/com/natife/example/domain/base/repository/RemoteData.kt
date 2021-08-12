package com.natife.example.domain.base.repository

import com.natife.example.domain.base.authorization.AuthorizationContract
import com.natife.example.domain.base.chat.RemoteChatContract
import com.natife.example.domain.base.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract