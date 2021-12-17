package com.example.groupproject.search_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_page.*
import java.util.ArrayList

/**
 * This class sets the search users activity
 */
class SearchActivity : AppCompatActivity(), SearchAdapter.ClickedItem{

    val db = Firebase.firestore
    var userArrayList = ArrayList<User>()
    var userAdapter: SearchAdapter? = null

    /**
     * This method sets the layout of the page and how the page will function
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        //create all fields to get the user information from database
        var fName: String
        var lName: String
        var username: String
        var bio: String
        var user: User

        //Change the action bar on top to show back arrow and change title
        val actionBar = supportActionBar
        actionBar!!.title = "Search Users"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //set user recycler view to linear
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        //Get the current user for removal from search list
        val currentUser = Firebase.auth.currentUser
        var tempUsername: String = ""
        if(currentUser != null){
            val uid = currentUser.uid
            db.collection("Account").get().addOnSuccessListener { result ->
                for(document in result){
                    if(document.getString("uid")!!.toString() == uid) {
                        tempUsername = document.getString("username")!!.toString()
                    }
                }
                //Search database for all users
                db.collection("Account")
                    .get()
                    .addOnSuccessListener { result1 ->
                        //put users into an arraylist to search through later
                        for(document in result1){
                            fName = document.getString("first")!!.toString()
                            lName = document.getString("last")!!.toString()
                            username = document.getString("username")!!.toString()
                            bio = document.getString("description")!!.toString()
                            //insert post here
                            user = User(username, fName, lName, bio)
                            if(username != tempUsername) {
                                userArrayList.add(user)
                            }
                        }

                        //set the adapter to the user list
                        userAdapter = SearchAdapter(this)
                        userAdapter!!.setData(userArrayList)
                        recyclerView.adapter = userAdapter
                    }
                    .addOnFailureListener { exception ->
                        //Send out a toast upon failure of a failure to load in profiles
                        Toast.makeText(this, "Failed to load in users", Toast.LENGTH_LONG).show()
                    }
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Could not load in user array correctly")
            }
        }






    }

    /**
     * This method is the clicked item
     * @param user: the user being clicked on
     */
    override fun clickedItem(user: User){
        var user1 = user
        //Testing purposes -- delete later
        Log.e("TAG", "====> " + user1.username)
        Toast.makeText(this@SearchActivity, "User " + user1.username + " clicked!", Toast.LENGTH_SHORT).show()

        //Go to user's page
        startActivity(Intent(this@SearchActivity,SearchedProfileActivity::class.java).putExtra("data", user1))
    }

    /**
     * This method creates the search menu on top of the screen
     * @param menu: the search menu
     * @return: whether the menu will show or not
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        //set the menu
        menuInflater.inflate(R.menu.menu, menu)
        var menuItem = menu!!.findItem(R.id.searchView)
        var searchView = menuItem.actionView as SearchView

        //create the searchview in teh menu
        searchView.maxWidth = Int.MAX_VALUE
        //set on text submitted/changed listener
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