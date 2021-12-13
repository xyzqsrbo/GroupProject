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
import kotlinx.android.synthetic.main.profile_page.*
import java.util.ArrayList

class SearchActivity : AppCompatActivity(), SearchAdapter.ClickedItem{

    var userList = arrayOf(
        User("FirstLast", "First", "Last", "Just your Average Joe",
            listOf(Post(R.drawable.ic_upvote, "FirstLast", "FirstLast0"),
                Post(R.drawable.ic_user_picture, "FirstLast", "FirstLast1")
            )),
        User("TheChosenOne", "Anikan", "Skywalker", "", listOf()),
        User("BenKenobi", "Obiwan", "Kenobi", "", listOf()),
        User("TheSenate", "Sheev", "Palpatine", "", listOf()),
        User("TheNewHope", "Luke", "Skywalker", "", listOf()),
        User("ThePrincess", "Leah", "Organa", "", listOf()),
        User("TheTraitor", "Count", "Dooku", "", listOf()),
        User("TheBountyHunter", "Boba", "Fett", "", listOf())
    )

    var userArrayList = ArrayList<User>()
    var userAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        for(user in userList){
            userArrayList.add(user)
        }

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