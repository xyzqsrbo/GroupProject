package com.example.groupproject.profile_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: Decmber 13, 2021
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.R
import com.example.groupproject.search_page.SearchActivity
import com.example.groupproject.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_page.*

/**
 * This class is the activity page for the logged in user profile
 */
class ProfileActivity : AppCompatActivity() {
    var user: User? = null
    private lateinit var auth: FirebaseAuth

    companion object{
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    /**
     * This method sets the view to the profile_page
     * @param savedInstanceState: the saved state of the page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        //Get all views in the page
        val btnSearchFriend: ImageButton = findViewById(R.id.btnSearchFriend)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        //Set the user to the logged in user
        user = UserSupplier.user
        //This is eventually going in
        //auth = Firebase.auth
        //val currentUser = auth.currentUser
        //user.username = currentUser


        //Change the action bar on top to username
        val actionBar = supportActionBar
        actionBar!!.title = user!!.username

        //Set all appropriate data to on the page
        profilePicture.setImageResource(R.drawable.ic_user_picture)
        textName.text = (user!!.fName + " " + user!!.lName)
        txtBiography.text = user!!.bio

        //Set on click listeners the search friend button and settings button
        btnSearchFriend.setOnClickListener{
            startActivity(Intent(this@ProfileActivity, SearchActivity::class.java))
        }
        btnSettings.setOnClickListener{
            Log.i(TAG, "setting button was clicked!")
            showToast("Settings button was clicked!")
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
                    }
                }
                //setup the recycler view to the logged in users posts
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
        //set the layout to linear and make the posts into a grid
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        //set the adapter to the posts
        val adapter = PostsAdapter(this, posts)
        recyclerView.adapter = adapter
    }
}