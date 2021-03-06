package com.example.groupproject

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.groupproject.profile_page.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class PostActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var locationText: EditText
    private lateinit var cancelButton: Button
    private lateinit var postButton: Button
    private lateinit var auth:FirebaseAuth
     lateinit var imageUri : Uri

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?
        ) {
            // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        button = findViewById(R.id.uploadImageButton)
        imageView = findViewById(R.id.addImage)
        editText = findViewById(R.id.description)
        locationText = findViewById(R.id.location)
        cancelButton = findViewById(R.id.cancel)
        postButton = findViewById(R.id.post)
        val storageRef = FirebaseStorage.getInstance().reference.child("Images")
        auth = FirebaseAuth.getInstance()
        cancelButton.setOnClickListener{
            startActivity(Intent(this, MainPage::class.java))
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
                        this,
                        "Please enter a location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter a description.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            uploadImage(locationText.text.toString(), editText.text.toString())
          //  postContent(locationText.text.toString(), editText.text.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
           // imageView.setImageURI(data?.data)
            imageUri = data?.data!!
            imageView.setImageURI(imageUri)
            button.setY(225F) // This will move the button down below the image so it is not overlapping it.
        }
    }
    private fun uploadImage(location: String, description: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("Images/$location")

        storageReference.putFile(imageUri).
                addOnSuccessListener {
                    imageView.setImageURI(null)
                    Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                    if(progressDialog.isShowing) progressDialog.dismiss()
                }
            .addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        val db2 = FirebaseFirestore.getInstance()
        val db = Firebase.firestore

        var bundle = intent.getBundleExtra("data") as Bundle
        db2.collection("Account").whereEqualTo("uid", auth.currentUser!!.uid).limit(1).get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val post = hashMapOf(
                        "uid" to auth.currentUser!!.uid,
                        "timestamp" to Timestamp(Date()),
                        "Name" to location,
                        "description" to description,
                        "likes" to 0,
                        "dislikes" to 0,
                        "username" to document.getString("username")!!.toString(),
                        "lat" to bundle.get("lat"),
                        "long" to bundle.get("long"),
                        "imageName" to "$location image"
                    )
                   // db.collection("Add Post").orderBy("Description", Query.Direction.DESCENDING)

                    // Set the database document to be the location of the post.
                    db.collection("Post").document(location).set(post)
                    Toast.makeText(this, "Successfully Post", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainPage::class.java))
                }

        }
        /*
        val post = hashMapOf(
            "uid" to auth.currentUser!!.uid,
            "timestamp" to Timestamp(Date()),
            "Name" to location,
            "description" to description,
            "likes" to 0,
            "dislikes" to 0,
            "username" to "",
            "lat" to 0,
            "long" to 0,
            "imageName" to "$location image"
        )
        db.collection("Add Post").orderBy("Description", Query.Direction.DESCENDING)
        // Set the database document to be the location of the post.
        db.collection("Post").document(location).set(post)
        Toast.makeText(activity, "Successfully Post", Toast.LENGTH_SHORT).show()
        startActivity(Intent(activity, InspectPostActivity::class.java))
        //finish()

         */
    }
    private fun cancel(){

    }
}