package com.natife.example.domain.chat

import com.natife.example.domain.UserRepository

interface ChatRepository : UserRepository, MessageRepository, SendMessage