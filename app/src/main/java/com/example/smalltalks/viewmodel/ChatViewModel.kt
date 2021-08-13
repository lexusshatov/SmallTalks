package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.natife.example.domain.chat.ChatContract
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.Message
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val chatRepository: ChatContract,
    @Assisted private val receiver: User
) : BaseViewModel<List<Message>>() {

    val me = chatRepository.me

    override val data: LiveData<List<Message>>
        get() = mutableData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getDialog(receiver).collect {
                println(it)
                mutableData.postValue(it)
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.sendMessage(receiver, message)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(receiver: User): ChatViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            receiver: User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(receiver) as T
            }
        }
    }
}