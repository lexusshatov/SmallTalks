package com.natife.example.domain.base.repository.local

import com.natife.example.domain.base.chat.LocalChatContract
import com.natife.example.domain.base.userlist.LocalUsersContract

interface LocalData : LocalChatContract, LocalUsersContract, PreferencesData