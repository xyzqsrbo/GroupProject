package com.example.groupproject

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InspectPostActivity : AppCompatActivity() {
    var likeCounter = 0
    var dislikeCounter = 0
    private lateinit var likeButton: Button
    private lateinit var dislikeButton: Button
    private lateinit var likeIncrementer: TextView
    private lateinit var dislikeIncrementer: TextView
    private lateinit var locationTextView: TextView
    private lateinit var previousArrow: Button
    private lateinit var nextArrow: Button
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_post)

        // Linking all of the elements to their views
        likeButton = findViewById(R.id.likeButton)
        likeIncrementer = findViewById(R.id.likeIncrementer)
        dislikeButton = findViewById(R.id.dislikeButton)
        dislikeIncrementer = findViewById(R.id.dislikeIncrementer)
        locationTextView = findViewById(R.id.locationView)
        previousArrow = findViewById(R.id.backArrow)
        nextArrow = findViewById(R.id.forwardArrow)

        reference = FirebaseDatabase.getInstance().reference

        // When clicked, it increments the like count
        likeButton.setOnClickListener {
            likeCounter++
            likeIncrementer.setText("$likeCounter")
        }
        // When clicked, it decrements the dislike counts
        dislikeButton.setOnClickListener {
            dislikeCounter++
            dislikeIncrementer.setText("$dislikeCounter")
        }
        // Sets the text to the database location
        locationTextView.setText(getLocation())

        nextArrow.setOnClickListener {

        }
    }
    /*
    private fun getDescription(): String {
        reference.child("Description").addChildEventListener()
    }
     */
    private fun getLocation(): String {
        val db = Firebase.firestore
        val userLocation = db.collection("Add Post").document()
       var location = userLocation.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return location.toString()
    }
}