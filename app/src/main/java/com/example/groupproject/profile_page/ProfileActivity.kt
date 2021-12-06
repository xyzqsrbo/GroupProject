package com.example.groupproject.profile_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.PostSupplier
import com.example.groupproject.R
import com.example.groupproject.showToast
import kotlinx.android.synthetic.main.profile_page.*

class ProfileActivity : AppCompatActivity() {
    companion object{
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        val btnAddFriend: ImageButton = findViewById(R.id.btnAddFriend)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)


        profilePicture.setImageResource(R.drawable.ic_user_picture)
        textName.text = UserSupplier.user.fName + " " + UserSupplier.user.lName
        txtBiography.text = UserSupplier.user.bio

        btnAddFriend.setOnClickListener{
            Log.i(TAG, "AddFriend button was clicked!")
            showToast("Add Friend button was clicked!")
        }

        btnSettings.setOnClickListener{
            Log.i(TAG, "setting button was clicked!")
            showToast("Settings button was clicked!")
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter = PostsAdapter(this, PostSupplier.post)
        recyclerView.adapter = adapter
    }
}