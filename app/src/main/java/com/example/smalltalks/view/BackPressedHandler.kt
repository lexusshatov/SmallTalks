package com.example.smalltalks.view

import com.example.smalltalks.model.repository.Destroyable
import com.example.smalltalks.view.base.OnBackPressed
import kotlinx.coroutines.*

class BackPressedHandler(
    private val actions: HashMap<Int, () -> Unit>,
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
        actions[clickCount]?.invoke()
    }

    override fun destroy() {
        job.cancel()
    }
}