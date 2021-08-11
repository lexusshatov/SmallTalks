package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.remote_protocol.User
import com.example.core.repository.base.chat.ChatContract
import com.example.core.repository.local.Message
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val decorator: com.example.core.repository.base.chat.ChatContract,
    @Assisted private val receiver: com.example.core.remote_protocol.User
) : BaseViewModel<List<com.example.core.repository.local.Message>>() {

    val me = decorator.me

    override val data: LiveData<List<com.example.core.repository.local.Message>>
        get() = decorator.getDialog(receiver)

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.sendMessage(receiver, message)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(receiver: com.example.core.remote_protocol.User): ChatViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            receiver: com.example.core.remote_protocol.User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(receiver) as T
            }
        }
    }
}