package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.natife.example.domain.remote.AuthorizationRepository
import com.natife.example.domain.remote.MessageRepository
import com.natife.example.domain.dto.User
import com.natife.example.domain.local.DialogRepository
import com.natife.example.domain.local.PreferencesRepository
import com.natife.example.domain.remote.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val authorizationRepository: AuthorizationRepository,
    private val dialogRepository: DialogRepository,
    private val usersRepository: UsersRepository,
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
            messageRepository.messages.collect {
                dialogRepository.saveMessage(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        CoroutineScope(Dispatchers.IO).launch {
            authorizationRepository.disconnect()
        }
    }
}