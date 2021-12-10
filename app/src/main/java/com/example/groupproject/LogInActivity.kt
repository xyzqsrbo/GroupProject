package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val actionBar = supportActionBar
        actionBar!!.title = "Sign In"
        actionBar.setDisplayHomeAsUpEnabled(true)

        email = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.editTextTextPassword)
        login = findViewById(R.id.button)
        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            var txt_email = email.text.toString()
            var txt_password = password.text.toString()

            loginUser(txt_email, txt_password);

            if (txt_email.isEmpty() || txt_password.isEmpty()) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(txt_email, txt_password);
            }
        }


    }

    private fun loginUser(txtEmail: String, txtPassword: String) {

        auth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener { result ->
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }
    }
}