package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.remote_protocol.BaseDto
import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.SendMessageDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.PrintWriter
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val gson: Gson,
) : BaseViewModel() {
    override lateinit var data: Flow<MessageDto>

    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter
    private lateinit var user: User

    fun start(input: BufferedReader, output: PrintWriter, user: User) {
        this.input = input
        this.output = output
        this.user = user

        data = flow {
            while (true) {
                val baseDto = gson.fromJson(
                    runCatching { input.readLine() }.getOrThrow(),
                    BaseDto::class.java
                )
                when (baseDto.action) {
                    BaseDto.Action.NEW_MESSAGE -> {
                        val messageDto = gson.fromJson(baseDto.payload, MessageDto::class.java)
                        emit(messageDto)
                    }
                    else -> {/*TODO*/
                    }
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO userid receiver
            val messageDto = SendMessageDto(user.id, user.id, message)
            val baseMessageDto = BaseDto(BaseDto.Action.SEND_MESSAGE, gson.toJson(messageDto))

            output.println(gson.toJson(baseMessageDto))
        }
    }
}