package com.example.smalltalks.view

import com.example.smalltalks.model.repository.base.Destroyable
import com.example.smalltalks.view.base.OnBackPressed
import kotlinx.coroutines.*

class BackPressedHandler(
    private val action: () -> Unit,
    private val exit: () -> Unit,
    private val clicksToExit: Int,
    clickDelay: Long = 1500L
) : OnBackPressed, Destroyable {
    private var clickCount = 0
    private val scope = CoroutineScope(Dispatchers.IO) + Job()

    private val job = scope.launch(Dispatchers.IO) {
        while (isActive) {
            delay(clickDelay)
            if (clickCount > 0) clickCount--
        }
    }

    override fun onBackPressed() {
        clickCount++
        if (clickCount >= clicksToExit) {
            exit()
        } else {
            action()
        }
    }

    override fun destroy() {
        job.cancel()
    }
}