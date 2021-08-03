package com.example.smalltalks

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@ExperimentalCoroutinesApi
suspend fun main() {
    val flow1 = flow {
        emit(1)
        emit(2)
    }

    val flow2 = flow {
        emit(3)
        emit(4)
    }

    merge(flow1, flow2).collect {
        println(it)
    }
}