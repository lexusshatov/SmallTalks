package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.flow

class ChatViewModel : BaseViewModel() {
    override val data by lazy {
        // TODO
        flow {
            emit(
                MessageDto(
                    User("3", "Bogdan"),
                    "Hello, world!"
                )
            )
            kotlinx.coroutines.delay(300)
            emit(
                MessageDto(
                    User("4", "Oleh"),
                    "Hello, too!"
                )
            )
        }
    }
}