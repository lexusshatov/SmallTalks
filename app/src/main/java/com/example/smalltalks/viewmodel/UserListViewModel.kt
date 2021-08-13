package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.natife.example.domain.dto.User
import com.natife.example.domain.userlist.UsersContract
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

    override val data: LiveData<List<User>>
        get() = mutableData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.users.collect {
                mutableData.postValue(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.messages.collect {
                usersRepository.saveMessage(it)
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