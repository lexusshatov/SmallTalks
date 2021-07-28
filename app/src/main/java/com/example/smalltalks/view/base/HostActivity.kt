package com.example.smalltalks.view.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smalltalks.R
import com.example.smalltalks.view.authorization.AuthorizationFragment
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
}