package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.natife.example.domain.chat.ChatRepository
import com.natife.example.domain.dto.User
import com.natife.example.domain.repository.local.LocalRepository
import com.natife.example.domain.repository.local.PreferencesRepository
import com.natife.example.domain.userlist.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val usersRepository: UsersRepository,
    private val localRepository: LocalRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<List<User>>(), PreferencesRepository by preferencesRepository {

    override val data: LiveData<List<User>>
        get() = mutableData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.users.collect {
                mutableData.postValue(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.messages.collect {
                localRepository.saveMessage(it)
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