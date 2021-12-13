package com.example.groupproject.profile_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.R
import com.example.groupproject.search_page.SearchActivity
import com.example.groupproject.showToast
import kotlinx.android.synthetic.main.profile_page.*

class ProfileActivity : AppCompatActivity() {

    var user: User? = null

    companion object{
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        val btnSearchFriend: ImageButton = findViewById(R.id.btnSearchFriend)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        user = UserSupplier.user

        profilePicture.setImageResource(R.drawable.ic_user_picture)
        textName.text = (user!!.fName + " " + user!!.lName)
        txtBiography.text = user!!.bio

        btnSearchFriend.setOnClickListener{
            startActivity(Intent(this@ProfileActivity, SearchActivity::class.java))
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
        val adapter = PostsAdapter(this, user!!.posts)
        recyclerView.adapter = adapter
    }
}