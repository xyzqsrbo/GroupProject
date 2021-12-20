package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    //global variables for the page
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var password2:EditText
    private lateinit var username:EditText
    private lateinit var fname:EditText
    private lateinit var lname:EditText
    private lateinit var description:EditText
    private lateinit var register:Button
    private lateinit var signin:Button
    private lateinit var auth:FirebaseAuth

    /*
        Do this when you load the page, set all my variables for text boxes and buttons as well as setup listeners for the buttons
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //setup the top bar to sign up
        val actionBar = supportActionBar
        actionBar!!.title = "Sign Up"

        //setup all my elements on the page to my variables
        email = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.editTextTextPassword)
        password2 = findViewById(R.id.editTextTextPassword2)
        username = findViewById(R.id.editTextTextUsername)
        fname = findViewById(R.id.editTextTextFname)
        lname = findViewById(R.id.editTextTextLname)
        description = findViewById(R.id.editTextTextBio)
        register = findViewById(R.id.register)
        signin = findViewById(R.id.signin)
        auth = FirebaseAuth.getInstance()

        //listener for the sign in button
        signin.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        //listener for the register button
        register.setOnClickListener {

            //get all the text fields values and store them in variables
            var txt_email = email.text.toString()
            var txt_password = password.text.toString()
            var txt_password2 = password2.text.toString()
            var txt_username = username.text.toString()
            var txt_fname = fname.text.toString()
            var txt_lname = lname.text.toString()
            var txt_description = description.text.toString()

            //error checking the input
            if (txt_email.isEmpty() || txt_password.isEmpty() || txt_username.isEmpty() || txt_fname.isEmpty() || txt_lname.isEmpty()) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            } else if (txt_password.length < 6) {
                Toast.makeText(this, "Password needs to be longer than 6 characters!", Toast.LENGTH_SHORT).show()
            } else if (!txt_password.equals(txt_password2)) {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            } else if (txt_description.length > 128) {
                Toast.makeText(this, "Description must be below 128 characters", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(txt_email, txt_password, txt_username, txt_fname, txt_lname, txt_description)
            }
        }
    }

    /*
        registers a user to the database

        @txtEmail: email of user
        @txtPassword: password of user
        @txtUsername: username of user
        @txtFname: first name of user
        @txtLname: last name of user
     */
    private fun registerUser(txtEmail: String, txtPassword: String, txtUsername: String, txtFname: String, txtLname: String, txtDescription: String) {
        // try to add user to the database
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener { task ->
            //check if it successfully added it
            if (task.isSuccessful) {
                val db = Firebase.firestore
                val user = hashMapOf (
                            "uid" to auth.currentUser!!.uid,
                            "username" to txtUsername,
                            "first" to txtFname,
                            "last" to txtLname,
                            "description" to txtDescription
                        )

                //add extra info not needed for auth linked ot the account in the Account collection
                db.collection("Account")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Registered user!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainPage::class.java))
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to register!", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Make sure you have a real email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}