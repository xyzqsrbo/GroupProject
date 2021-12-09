package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var register:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar
        actionBar!!.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)

        email = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.editTextTextPassword)
        register = findViewById(R.id.button)
        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {

            var txt_email = email.text.toString()
            var txt_password = password.text.toString()

            if (txt_email.isEmpty() || txt_password.isEmpty()) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            } else if (txt_password.length < 6) {
                Toast.makeText(this, "Password needs to be longer than 6 characters!", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(txt_email, txt_password)
            }

            //startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun registerUser(txtEmail: String, txtPassword: String) {
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registered user!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Make sure you have a real email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}