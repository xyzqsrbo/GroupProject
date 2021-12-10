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

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var password2:EditText
    private lateinit var username:EditText
    private lateinit var fname:EditText
    private lateinit var lname:EditText
    private lateinit var register:Button
    private lateinit var signin:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar
        actionBar!!.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)

        email = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.editTextTextPassword)
        password2 = findViewById(R.id.editTextTextPassword2)
        username = findViewById(R.id.editTextTextUsername)
        fname = findViewById(R.id.editTextTextFname)
        lname = findViewById(R.id.editTextTextLname)
        register = findViewById(R.id.register)
        signin = findViewById(R.id.signin)
        auth = FirebaseAuth.getInstance()

        signin.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        register.setOnClickListener {

            var txt_email = email.text.toString()
            var txt_password = password.text.toString()
            var txt_password2 = password2.text.toString()
            var txt_username = username.text.toString()
            var txt_fname = fname.text.toString()
            var txt_lname = lname.text.toString()

            if (txt_email.isEmpty() || txt_password.isEmpty() || txt_username.isEmpty() || txt_fname.isEmpty() || txt_lname.isEmpty()) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            } else if (txt_password.length < 6) {
                Toast.makeText(this, "Password needs to be longer than 6 characters!", Toast.LENGTH_SHORT).show()
            } else if (!txt_password.equals(txt_password2)) {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(txt_email, txt_password, txt_username, txt_fname, txt_lname)
            }
        }
    }

    private fun registerUser(txtEmail: String, txtPassword: String, txtUsername: String, txtFname: String, txtLname: String) {
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val db = Firebase.firestore
                val user = hashMapOf (
                            "uid" to auth.currentUser!!.uid,
                            "username" to txtUsername,
                            "first" to txtFname,
                            "last" to txtLname
                        )

                db.collection("Account")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Registered user!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LogInActivity::class.java))
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