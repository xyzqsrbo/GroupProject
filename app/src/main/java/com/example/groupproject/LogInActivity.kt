package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val actionBar = supportActionBar
        actionBar!!.title = "Sign In"
        actionBar.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}