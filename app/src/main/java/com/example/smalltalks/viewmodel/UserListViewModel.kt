package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.repository.base.userlist.UserListContract
import com.example.smalltalks.model.repository.local.Message
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    decorator: UserListContract
) : BaseViewModel() {

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

    override val data = decorator.users
}