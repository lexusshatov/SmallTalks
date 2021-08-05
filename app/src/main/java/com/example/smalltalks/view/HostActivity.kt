package com.example.smalltalks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smalltalks.R
import com.example.smalltalks.view.authorization.AuthorizationFragment
import com.example.smalltalks.view.base.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, AuthorizationFragment())
            .commit()


    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        (currentFragment as? IOnBackPressed).apply {
            if (this != null) {
                onBackPressed()
            } else {
                super.onBackPressed()
            }
        }
    }
}