package com.example.smalltalks.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.smalltalks.R
import com.example.smalltalks.view.authorization.AuthorizationFragment
import com.example.smalltalks.view.authorization.AuthorizationFragment.Companion.USER_NAME
import com.example.smalltalks.view.authorization.AuthorizationFragment.Companion.USER_PREFERENCES
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HostActivity : AppCompatActivity() {
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, AuthorizationFragment.newInstance())
            .commit()

        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(TIMEOUT_EXIT)
                if (clickCount > 0) clickCount--
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == CHAT_FRAGMENT_ENTRY_COUNT) {
            clickCount++
            when(clickCount) {
                1 -> Toast.makeText(baseContext, "Click again for log out", Toast.LENGTH_LONG).show()
                2 -> {
                    getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                        .edit {
                            remove(USER_NAME)
                            apply()
                        }
                    finish()
                    startActivity(intent)
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val CHAT_FRAGMENT_ENTRY_COUNT = 1
        const val TIMEOUT_EXIT = 1500L
    }
}