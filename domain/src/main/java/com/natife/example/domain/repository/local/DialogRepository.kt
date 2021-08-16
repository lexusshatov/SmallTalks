package com.natife.example.domain.repository.local

import com.natife.example.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface DialogRepository: SaveRepository {

    fun getDialog(receiver: User): Flow<List<Message>>
}