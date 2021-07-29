package com.example.smalltalks.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.smalltalks.R
import com.example.smalltalks.view.authorization.AuthorizationFragment
import com.example.smalltalks.view.authorization.AuthorizationFragment.Companion.USER_NAME
import com.example.smalltalks.view.authorization.AuthorizationFragment.Companion.USER_PREFERENCES
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, AuthorizationFragment.newInstance())
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == CHAT_FRAGMENT_ENTRY_COUNT) {
            getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                .edit {
                    remove(USER_NAME)
                    apply()
                }
            finish()
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val CHAT_FRAGMENT_ENTRY_COUNT = 1
    }
}