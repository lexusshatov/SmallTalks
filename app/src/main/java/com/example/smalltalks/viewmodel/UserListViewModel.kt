package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain.remote_protocol.User
import com.example.domain.repository.base.userlist.UsersContract
import com.example.domain.repository.local.Message
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val decorator: com.example.domain.repository.base.userlist.UsersContract
) : BaseViewModel<List<com.example.domain.remote_protocol.User>>() {

    override val data = decorator.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.messages.collect {
                val messageDb = com.example.domain.repository.local.Message(
                    from = it.from,
                    to = decorator.me,
                    message = it.message
                )
                decorator.saveMessage(messageDb)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        CoroutineScope(Dispatchers.IO).launch {
            decorator.disconnect()
        }
    }
}