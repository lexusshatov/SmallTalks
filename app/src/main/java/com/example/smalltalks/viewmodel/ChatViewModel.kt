package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.repository.ChatContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatContract
) : BaseViewModel() {

    val me = repository.me
    override val data = repository.messages

    fun sendMessage(to: String, message: String) {
        repository.sendMessage(to, message)
    }
}