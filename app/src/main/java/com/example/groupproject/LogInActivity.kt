package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    //global variables for the page
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var register:Button
    private lateinit var auth:FirebaseAuth

    /*
        Do this when page is entered, create listeners for all buttons and setup variables to every element of the page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        //set the top bar to say sign in
        val actionBar = supportActionBar
        actionBar!!.title = "Sign In"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //assign every element to a global variable
        email = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.editTextTextPassword)
        login = findViewById(R.id.login)
        register = findViewById(R.id.register)
        auth = FirebaseAuth.getInstance()

        //listener for the register button
        register.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //listener for the login button
        login.setOnClickListener {
            //get the text from the fields as variables
            var txt_email = email.text.toString()
            var txt_password = password.text.toString()

            //error checking for user input
            if (txt_email.isEmpty() || txt_password.isEmpty()) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(txt_email, txt_password);
            }
        }


    }

    /*
        logs in the user from the given credentials

        @txtEmail: email of the user
        @txtPassword: password of the user
     */
    private fun loginUser(txtEmail: String, txtPassword: String) {
        //login the user
        auth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener { result ->
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainPage::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to login!", Toast.LENGTH_SHORT).show()
        }

    }
}