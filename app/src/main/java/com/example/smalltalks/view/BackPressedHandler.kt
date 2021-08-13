package com.example.smalltalks.view

import com.example.smalltalks.view.base.OnBackPressed
import kotlinx.coroutines.*
import java.io.Closeable

class BackPressedHandler(
    private val action: () -> Unit,
    private val exit: () -> Unit,
    private val clicksToExit: Int,
    private val clickDelay: Long = 1500L
) : OnBackPressed, Closeable {
    private var clickCount = 0
    private val scope = CoroutineScope(Dispatchers.IO) + Job()

    private var job: Job? = null

    override fun onBackPressed() {
        job?.cancel()
        job = scope.launch(Dispatchers.IO) {
            delay(clickDelay)
            clickCount = 0
        }
        clickCount++
        if (clickCount >= clicksToExit) {
            exit()
        } else {
            action()
        }
    }

    override fun close() {
        job?.cancel()
    }
}