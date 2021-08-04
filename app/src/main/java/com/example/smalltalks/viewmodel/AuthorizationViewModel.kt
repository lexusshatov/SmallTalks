package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.di.decorator.Decorator
import com.example.smalltalks.model.repository.AuthorizationContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    @Decorator private val repository: AuthorizationContract
) : BaseViewModel() {

    override val data = repository.connect

    fun connect(userName: String) {
        repository.connect(userName)
    }
}