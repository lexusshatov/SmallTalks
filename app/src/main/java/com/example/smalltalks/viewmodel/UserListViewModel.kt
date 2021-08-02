package com.example.smalltalks.viewmodel

import com.example.smalltalks.model.repository.SocketRepository
import com.example.smalltalks.model.repository.UserListContract
import com.example.smalltalks.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserListContract
) : BaseViewModel() {

    override val data = repository.users

}