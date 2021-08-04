package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.repository.base.AuthorizationContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val decorator: AuthorizationContract
) : BaseViewModel() {

    override val data = decorator.connect

    fun connect(userName: String) {
        decorator.connect(userName)
    }

    override fun onCleared() {
        super.onCleared()
        decorator.disconnect()
    }
}