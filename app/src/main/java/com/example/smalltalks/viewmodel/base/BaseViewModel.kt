package com.example.smalltalks.viewmodel.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<T>: ViewModel() {

    abstract val data: Flow<T>
}