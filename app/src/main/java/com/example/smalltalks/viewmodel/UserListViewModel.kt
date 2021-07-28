package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.remote_protocol.UsersReceivedDto
import com.example.smalltalks.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.flow

class UserListViewModel : BaseViewModel() {
    override val data by lazy {
        //TODO
        flow {
            //Socket query
            emit(
                UsersReceivedDto(
                    listOf(
                        User("1", "Egor"),
                        User("2", "Artem")
                    )
                )
            )
        }
    }
}