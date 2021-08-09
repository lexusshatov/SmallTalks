package com.example.smalltalks.model.repository.base.repository

import com.example.smalltalks.model.repository.base.chat.LocalChatContract
import com.example.smalltalks.model.repository.base.userlist.LocalUsersContract

interface LocalData : LocalChatContract, LocalUsersContract, PreferencesData