package com.example.smalltalks.view.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.example.smalltalks.R
import com.example.smalltalks.view.AuthorizationFragment

class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, AuthorizationFragment.newInstance())
            .commit()
    }


}