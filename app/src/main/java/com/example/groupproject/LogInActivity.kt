package com.example.groupproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val actionBar = supportActionBar
        actionBar!!.title = "Log In"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}