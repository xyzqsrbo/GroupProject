package com.example.groupproject.search_page

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.R
import com.example.groupproject.profile_page.Post
import com.example.groupproject.profile_page.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_page.*
import java.util.ArrayList

class SearchActivity : AppCompatActivity(), SearchAdapter.ClickedItem{

    val db = Firebase.firestore

    var userArrayList = ArrayList<User>()
    var userAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        var user = User("", "", "", "", listOf())

        db.collection("Account")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    user.fName = document.getString("first")!!.toString()
                    user.lName = document.getString("last")!!.toString()
                    user.username = document.getString("username")!!.toString()
                    user.bio = document.getString("description")!!.toString()
                    //insert post here
                    user.posts = listOf()
                    userArrayList.add(user)
                    Log.e("TAG", "====>" + user.username)
                }

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load in users", Toast.LENGTH_LONG).show()
            }

        val actionBar = supportActionBar
        actionBar!!.title = "Search Users"
        actionBar.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userAdapter = SearchAdapter(this)
        userAdapter!!.setData(userArrayList)
        recyclerView.adapter = userAdapter
    }

    override fun clickedItem(user: User){
        var user1 = user
        Log.e("TAG", "====> " + user1.username)
        Toast.makeText(this@SearchActivity, "User " + user1.username + " clicked!", Toast.LENGTH_SHORT).show()

        //For later implementation
        //Go to user's page
        startActivity(Intent(this@SearchActivity,SearchedProfileActivity::class.java).putExtra("data", user1))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu, menu)
        var menuItem = menu!!.findItem(R.id.searchView)
        var searchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean{
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean{
                Log.i("TAG", "====>$p0")
                userAdapter!!.filter.filter(p0)
                return true
            }
        })

        return true
    }
}