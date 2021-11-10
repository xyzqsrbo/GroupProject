package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar
        actionBar!!.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}