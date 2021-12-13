package com.example.groupproject.search_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.R
import com.example.groupproject.profile_page.PostsAdapter
import com.example.groupproject.profile_page.ProfileActivity
import com.example.groupproject.profile_page.User
import com.example.groupproject.showToast
import kotlinx.android.synthetic.main.activity_searched_profile.*

class SearchedProfileActivity : AppCompatActivity() {

    var user: User? = null

    companion object{
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_profile)

        val btnAddFriend: ImageButton = findViewById(R.id.btnAddFriend)

        user = intent.getSerializableExtra("data") as User

        profileSearchPicture.setImageResource(R.drawable.ic_user_picture)
        textSearchName.text = (user!!.fName + " " + user!!.lName)
        txtSearchBiography.text = user!!.bio

        btnAddFriend.setOnClickListener{
            Log.i(TAG, "add friend button was clicked!")
            showToast("add friend button was clicked!")
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        recyclerViewSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewSearch.layoutManager = GridLayoutManager(this, 3)
        val adapter = PostsAdapter(this, user!!.posts)
        recyclerViewSearch.adapter = adapter
    }
}