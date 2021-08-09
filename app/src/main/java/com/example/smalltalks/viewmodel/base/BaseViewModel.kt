package com.example.smalltalks.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Data>: ViewModel() {

    abstract val data: LiveData<Data>
}