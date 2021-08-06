package com.example.smalltalks.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.smalltalks.model.repository.base.authorization.AuthorizationContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val decorator: AuthorizationContract
) : BaseViewModel() {

    override val data = decorator.connectState

    fun connect(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            decorator.connect(userName)
        }
    }

}