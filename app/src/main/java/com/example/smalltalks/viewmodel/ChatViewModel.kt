package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.di.Decorator
import com.example.smalltalks.model.repository.remote.ChatContract
import com.example.smalltalks.model.repository.remote.LocalChatContract
import com.example.smalltalks.view.chat.MessageItem
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @Decorator private val repository: LocalChatContract,
) : BaseViewModel() {

    val me = repository.me
    override val data
        get() = repository.messages

    fun saveMessage(messageItem: MessageItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMessage(messageItem)
        }
    }

    fun sendMessage(to: String, message: String) {
        repository.sendMessage(to, message)
    }
}