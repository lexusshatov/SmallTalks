package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.MessageDto
import com.example.smalltalks.model.User
import com.example.smalltalks.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.flow

class ChatViewModel : BaseViewModel<MessageDto>() {
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