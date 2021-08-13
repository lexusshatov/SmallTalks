package com.natife.example.domain.repository.local

import com.natife.example.domain.chat.LocalChatContract
import com.natife.example.domain.userlist.LocalUsersContract

interface LocalData : LocalChatContract, LocalUsersContract, PreferencesData