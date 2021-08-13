package com.example.smalltalks.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Data>: ViewModel() {

    protected val mutableData = MutableLiveData<Data>()
    abstract val data: LiveData<Data>
}