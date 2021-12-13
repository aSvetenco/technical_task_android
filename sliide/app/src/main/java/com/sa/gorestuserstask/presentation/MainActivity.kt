package com.sa.gorestuserstask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.presentation.ui.users.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, UserListFragment())
                .commit()
        }
    }
}
