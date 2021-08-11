package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.repository.base.authorization.AuthorizationContract
import com.example.core.repository.remote.ConnectState
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val decorator: AuthorizationContract
) : BaseViewModel<ConnectState>() {

    private val mutableData = MutableLiveData<com.example.core.repository.remote.ConnectState>()
    override val data: LiveData<com.example.core.repository.remote.ConnectState>
        get() = mutableData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.connectState.collect {
                mutableData.postValue(it)
            }
        }
    }

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.connect(userName)
        }
    }
}