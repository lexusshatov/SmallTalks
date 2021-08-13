package com.natife.example.domain.repository.local

import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface LocalRepository: SaveRepository {

    fun getDialog(receiver: User): Flow<List<Message>>
}