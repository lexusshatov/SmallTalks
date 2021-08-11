package com.example.domain.repository.base.repository

import com.example.domain.repository.base.chat.LocalChatContract
import com.example.domain.repository.base.userlist.LocalUsersContract

interface LocalData : LocalChatContract, LocalUsersContract, PreferencesData