package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.viewmodel.base.BaseViewModel
import com.natife.example.domain.authorization.AuthorizationRepository
import com.natife.example.domain.local.PreferencesRepository
import com.natife.example.domain.ConnectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authRepository: AuthorizationRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<ConnectState>(), PreferencesRepository by preferencesRepository {

    override val data: LiveData<ConnectState>
        get() = mutableData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.connectState.collect {
                mutableData.postValue(it)
            }
        }
    }

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.connect(userName)
        }
    }
}