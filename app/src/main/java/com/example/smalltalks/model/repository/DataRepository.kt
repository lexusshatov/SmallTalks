package com.example.smalltalks.model.repository

import com.example.smalltalks.model.repository.remote.AuthorizationContract
import com.example.smalltalks.model.repository.remote.ChatContract
import com.example.smalltalks.model.repository.remote.UserListContract
import com.example.smalltalks.view.chat.MessageItem

interface DataRepository : AuthorizationContract, ChatContract, UserListContract