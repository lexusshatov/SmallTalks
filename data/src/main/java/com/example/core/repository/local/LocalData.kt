package com.example.core.repository.local

import com.example.core.chat.LocalChatContract
import com.example.core.userlist.LocalUsersContract

interface LocalData : LocalChatContract,
    LocalUsersContract,
    PreferencesData