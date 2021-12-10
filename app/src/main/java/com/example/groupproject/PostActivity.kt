package com.example.groupproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.text.HtmlCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth

class PostActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var locationText: EditText
    private lateinit var cancelButton: Button
    private lateinit var postButton: Button
    private lateinit var auth:FirebaseAuth

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        button = findViewById(R.id.uploadImageButton)
        imageView = findViewById(R.id.addImage)
        editText = findViewById(R.id.description)
        locationText = findViewById(R.id.location)
        cancelButton = findViewById(R.id.cancel)
        postButton = findViewById(R.id.post)
        auth = FirebaseAuth.getInstance()

        // This button will just send the user back to the previous activity
        cancelButton.setOnClickListener{
            finish()
        }
        button.setOnClickListener{
            pickImageGallery()
        }
        // This is to change the background color once the user types something in to be transparent.
        val originalColor = (editText.background as ColorDrawable).color
        val haveTextColor = Color.TRANSPARENT
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable) {
                editText.setBackgroundColor((if (s.isEmpty()) originalColor else haveTextColor))
            }
        })
        // Same thing except for the location text field.
        locationText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable) {
                locationText.setBackgroundColor((if (s.isEmpty()) originalColor else haveTextColor))
            }
        })
        // This will add to the database when the button is clicked
        postButton.setOnClickListener{
            when {
                TextUtils.isEmpty(locationText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@PostActivity,
                        "Please enter a location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@PostActivity,
                        "Please enter a description.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            postContent(locationText.text.toString(), editText.text.toString())
            /*
            else -> {
            val location: String = locationText.text.toString().trim { it <= ' '}
        }
             */
            // Creating variables for values in location texts.
            val location = locationText.text.toString()
        }

        // I want to have an <hr> tag below the location and description.
        val locationTextView: TextView = findViewById(R.id.locationView)
        val locationTextImage = getString(R.string.location_of_the_image)
        val tagHandler = HtmlTagHandler()
        locationTextView.text = HtmlCompat.fromHtml(locationTextImage, HtmlCompat.FROM_HTML_MODE_LEGACY, null, tagHandler)

        /*
        val html = "Hello <br> hai<br> I am fine <hr> Another line here <hr><hr>"
        val tagHandler = HtmlTagHandler()

        textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY, null, tagHandler)
         */
    }
    private fun pickImageGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    private fun postContent(location: String, description: String) {
        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore
        val post = hashMapOf(
            "uid" to auth.currentUser!!.uid,
            "titleLocation" to location,
            "Description" to description
        )
        // Add a new document with a generated ID
        db.collection("Add Post")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
            button.setY(225F) // This will move the button down below the image so it is not overlapping it.
        }
    }
    public final fun cancel(view: View): Unit{
        finish()
    }
}