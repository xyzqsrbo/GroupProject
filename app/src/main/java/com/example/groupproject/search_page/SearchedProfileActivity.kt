package com.example.groupproject.search_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.R
import com.example.groupproject.profile_page.Post
import com.example.groupproject.profile_page.PostsAdapter
import com.example.groupproject.profile_page.ProfileActivity
import com.example.groupproject.profile_page.User
import com.example.groupproject.showToast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_searched_profile.*

/**
 * This class is the setter for the searched user's profile page
 */
class SearchedProfileActivity : AppCompatActivity() {

    var user: User? = null

    companion object{
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    /**
     * This method sets the page views and also updates functionality of the page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_profile)
        val btnAddFriend: ImageButton = findViewById(R.id.btnAddFriend)

        //Get the searched user from previous view
        user = intent.getSerializableExtra("data") as User

        //Set the action bar to the correct title and put up a return arrow
        val actionBar = supportActionBar
        actionBar!!.title = user!!.username
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Set page data
        profileSearchPicture.setImageResource(R.drawable.ic_user_picture)
        textSearchName.text = (user!!.fName + " " + user!!.lName)
        txtSearchBiography.text = user!!.bio

        //Set on click listener to add friends
        btnAddFriend.setOnClickListener{
            Log.i(TAG, "add friend button was clicked!")
            showToast("add friend button was clicked!")
        }

        val db = Firebase.firestore
        var username: String
        var posts = ArrayList<Post>()
        var post: Post
        var postId: String
        var picture: Int
        //Search database for user's posts
        db.collection("Post")
            .get()
            .addOnSuccessListener { result ->
                //get the posts that are made by the user
                for(document in result){
                    username = document.getString("username")!!.toString()
                    if(username == user!!.username){
                        postId = document.getString("Name")!!.toString()
                        //change this later
                        picture = R.drawable.ic_user_picture
                        post = Post(picture, username, postId)
                        posts.add(post)
                        Log.i("TAG", "====>$postId")
                    }
                }
                //Setup the posts recycler view
                setupRecyclerView(posts)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load in posts", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * This method sets up the recycler view to the posts for the page
     */
    private fun setupRecyclerView(posts: ArrayList<Post>){
        //Set the layout of the recycler view
        recyclerViewSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewSearch.layoutManager = GridLayoutManager(this, 3)

        //Set the adapter to the recycler view
        val adapter = PostsAdapter(this, posts)
        recyclerViewSearch.adapter = adapter
    }
}