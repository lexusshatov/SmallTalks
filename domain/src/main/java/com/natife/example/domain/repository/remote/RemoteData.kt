package com.natife.example.domain.repository.remote

import com.natife.example.domain.authorization.AuthorizationContract
import com.natife.example.domain.chat.RemoteChatContract
import com.natife.example.domain.userlist.RemoteUsersContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUsersContract