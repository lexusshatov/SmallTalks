package com.example.smalltalks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.model.repository.remote.ConnectState
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val decorator: AuthorizationContract
) : BaseViewModel<ConnectState>() {

    private val mutableData = MutableLiveData<ConnectState>()
    override val data: LiveData<ConnectState>
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