package com.example.groupproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class InspectPostActivity : AppCompatActivity() {
    var likeCounter = 0
    var dislikeCounter = 0
    private lateinit var likeButton: Button
    private lateinit var dislikeButton: Button
    private lateinit var likeIncrementer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_post)

        likeButton = findViewById(R.id.likeButton)
        likeIncrementer = findViewById(R.id.likeIncrementer)
        dislikeButton = findViewById(R.id.dislikeButton)

        likeButton.setOnClickListener {
            likeCounter++
            likeIncrementer.setText("$likeCounter")
        }
        /*
        dislikeButton.setOnClickListener {
            dislikeCounter++
        }
         */
    }
}