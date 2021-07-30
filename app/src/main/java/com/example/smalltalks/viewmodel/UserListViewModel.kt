package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.remote_protocol.BaseDto
import com.example.smalltalks.model.remote_protocol.GetUsersDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.model.remote_protocol.UsersReceivedDto
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.PrintWriter
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val gson: Gson
) : BaseViewModel() {
    private lateinit var user: User
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter

    override val data by lazy {
        val getUsersDto = GetUsersDto(user.id)
        val baseGetUsersDto = BaseDto(BaseDto.Action.GET_USERS, gson.toJson(getUsersDto))
        val getUsersDtoJson = gson.toJson(baseGetUsersDto)

        flow {
            while (true) {
                output.println(getUsersDtoJson)
                val usersDto = runCatching { input.readLine() }.getOrThrow()
                val userList = gson.fromJson(usersDto, UsersReceivedDto::class.java)
                emit(userList.users)
                delay(5000L)
            }

        }
    }

    fun start(input: BufferedReader, output: PrintWriter, user: User) {
        this.input = input
        this.output = output
        this.user = user
    }
}