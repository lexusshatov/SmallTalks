package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core.dto.User
import com.example.core.repository.local.Message
import com.example.core.userlist.UsersContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val usersRepository: UsersContract
) : BaseViewModel<List<User>>() {

    override val data = usersRepository.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.messages.collect {
                val messageDb = Message(
                    from = it.from,
                    to = usersRepository.me,
                    message = it.message
                )
                usersRepository.saveMessage(messageDb)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.disconnect()
        }
    }
}