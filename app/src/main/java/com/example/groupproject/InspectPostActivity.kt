package com.example.groupproject

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.sql.Timestamp
import java.util.Date

class InspectPostActivity : AppCompatActivity() {
    var liked = false
    var likeCounter = 0
    var disliked = false
    var dislikeCounter = 0
    var nextClicker = 2 // Since the original page pop up will be 1, this will be the one after it
    var postSize = 0
    var actualPostSize = 0
    private lateinit var likeButton: Button
    private lateinit var dislikeButton: Button
    private lateinit var likeIncrementer: TextView
    private lateinit var dislikeIncrementer: TextView
    private lateinit var locationTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var previousArrow: Button
    private lateinit var nextArrow: Button
    private lateinit var usersImage: ImageView
    private lateinit var commentSection: TextView
    private lateinit var date: Timestamp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_post)

        // Linking all of the elements to their views
        likeButton = findViewById(R.id.likeButton)
        likeIncrementer = findViewById(R.id.likeIncrementer)
        dislikeButton = findViewById(R.id.dislikeButton)
        dislikeIncrementer = findViewById(R.id.dislikeIncrementer)
        locationTextView = findViewById(R.id.locationView)
        descriptionTextView = findViewById(R.id.description)
        previousArrow = findViewById(R.id.backArrow)
        nextArrow = findViewById(R.id.forwardArrow)
        usersImage = findViewById(R.id.usersImage)
        commentSection = findViewById(R.id.commentSection)

        val db = FirebaseFirestore.getInstance()
        val first = db.collection("Post").orderBy("timestamp")
        getNext(first, db, 2) // This is the original pop up
        getPostSizeAsync(first) { postSize ->
          //  commentSection.setText(postSize.toString())  // I was trying to display the number in the comment to see if it is actually returning the size
            nextArrow.setOnClickListener {
                nextClicker++
                if(nextClicker == postSize) {
                    nextClicker = 2
                }
                getNext(first, db, nextClicker)
            }
        }
        // When clicked, it increments the like count
        likeButton.setOnClickListener {
            if (!liked) {
                liked = true
                if (disliked) {
                    disliked = false
                    dislikeCounter--
                    dislikeIncrementer.setText("$dislikeCounter")
                }
                likeCounter++
                likeIncrementer.setText("$likeCounter")
            }
        }
        // When clicked, it decrements the dislike counts
        dislikeButton.setOnClickListener {
            if (!disliked) {
                disliked = true
                if (liked) {
                    liked = false
                    likeCounter--
                    likeIncrementer.setText("$likeCounter")
                }
                dislikeCounter++
                dislikeIncrementer.setText("$dislikeCounter")
            }
        }
        // This is for the previous button clicker
        previousArrow.setOnClickListener {
            if(nextClicker > 1)
            {
                nextClicker--
                getNext(first, db, nextClicker)
            }
        }
    }
    private fun getNext(first: Query, db: FirebaseFirestore, indexFam: Int) {
        first.get().addOnSuccessListener { queryDocumentSnapshots ->
            val lastVisible = queryDocumentSnapshots.documents[queryDocumentSnapshots.size() - indexFam]
            val next = db.collection("Post").orderBy("timestamp").startAfter(lastVisible).limit(1)
            next.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => $${document.data}")
                    locationTextView.setText(document.getString("Name"))
                    descriptionTextView.setText(document.getString("description"))
                    val imageName = document.getString("Name")
                    val storageRef = FirebaseStorage.getInstance().reference.child("Images/$imageName")
                    val localFile = File.createTempFile("tempImage", "jpeg")
                    storageRef.getFile(localFile).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        usersImage.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    // This is to get the size of the database collection of documents. It needs to be in a callback.
    private fun getPostSizeAsync(first: Query, callback: (Int) -> Unit) {
        first.get().addOnSuccessListener { documents ->
            val postSize = documents.count()
            callback(postSize)
        }
    }
}