package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.natife.example.domain.base.repository.local.Message
import com.natife.example.domain.base.userlist.UsersContract
import com.natife.example.domain.base.dto.User
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val decorator: UsersContract
) : BaseViewModel<List<User>>() {

    override val data = decorator.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.messages.collect {
                val messageDb = Message(
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