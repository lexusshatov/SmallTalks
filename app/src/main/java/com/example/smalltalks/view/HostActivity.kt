package com.example.smalltalks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smalltalks.R

class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, AuthorizationFragment.newInstance())
            .commit()
    }
}