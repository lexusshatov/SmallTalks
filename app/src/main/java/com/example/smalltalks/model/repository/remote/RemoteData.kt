package com.example.smalltalks.model.repository.remote

import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.model.repository.base.chat.RemoteChatContract
import com.example.smalltalks.model.repository.base.userlist.RemoteUserListContract

interface RemoteData : AuthorizationContract, RemoteChatContract, RemoteUserListContract