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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupproject.InspectPostActivity
import com.example.groupproject.R
import com.example.groupproject.search_page.SearchActivity
import com.example.groupproject.showToast
import com.example.groupproject.update
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_page.*

/**
 * This class is the activity page for the logged in user profile
 */
class ProfileActivity : Fragment(), PostsAdapter.ClickedItem {
    var user: User? = null
    private lateinit var auth: FirebaseAuth
    var postAdapter: PostsAdapter? = null
    private lateinit var main: View

    companion object {
        val TAG: String = ProfileActivity::class.java.simpleName
    }

    /**
     * This method sets the view to the profile_page
     * @param savedInstanceState: the saved state of the page
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        main = inflater.inflate(R.layout.profile_page, container, false)


        //Get all views in the page
        val btnSearchFriend: ImageButton = main.findViewById(R.id.btnSearchFriend)
        val btnSettings: ImageButton = main.findViewById(R.id.btnSettings)

        val db = Firebase.firestore
        //user = UserSupplier.user
        var fName: String = "Loading"
        var lName: String = "..."
        var username: String = "Loading..."
        var bio: String = "Loading..."
        var user: User
        //Set the user to the logged in user
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            db.collection("Account").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.getString("uid")!!.toString() == uid) {
                        fName = document.getString("first")!!.toString()
                        lName = document.getString("last")!!.toString()
                        username = document.getString("username")!!.toString()
                        bio = document.getString("description")!!.toString()
                        user = User(username, fName, lName, bio)
                        break
                    }
                }
                //Set all appropriate data to on the page
                profilePicture.setImageResource(R.drawable.ic_user_picture)
                textName.text = ("$fName $lName")
                txtBiography.text = bio
            }.addOnFailureListener { exception ->
                Toast.makeText(activity, "Failed to find a profile", Toast.LENGTH_SHORT).show()
                textName.text = ("Failed to Load in")
                txtBiography.text = ("Failed to load in the user profile")
            }
        } else {
            Toast.makeText(activity, "Failed to load in profile", Toast.LENGTH_SHORT).show()
            textName.text = ("Failed to Load in")
            txtBiography.text = ("Failed to load in the user profile")
        }

        //Set on click listeners the search friend button and settings button
        btnSearchFriend.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
        btnSettings.setOnClickListener {
            Log.i(TAG, "setting button was clicked!")
            activity?.showToast("Settings button was clicked!")
            startActivity(Intent(activity, update::class.java))
        }

        var post: Post
        var postId: String
        var picture: Int
        var postUsername: String
        var posts: ArrayList<Post> = arrayListOf()
        //Search database for user's posts
        db.collection("Post")
            .get()
            .addOnSuccessListener { result ->
                //get the posts that are made by the user
                for (document in result) {
                    postUsername = document.getString("username")!!.toString()
                    if (postUsername == username) {
                        postId = document.getString("Name")!!.toString()
                        //change this later
                        picture = R.drawable.ic_user_picture
                        post = Post(picture, username, postId)
                        posts.add(post)
                    }
                }
                //setup the recycler view to the logged in users posts
                setupRecyclerView(posts)
                postAdapter = PostsAdapter(this)
                postAdapter!!.setData(posts)
                recyclerView.adapter = postAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Failed to load in posts", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Failed to load in posts")
            }
        return main
    }

    override fun clickedItem(post: Post) {
        var post1 = post
        startActivity(Intent(activity, InspectPostActivity::class.java).putExtra("data", post1.username))
    }

    /**
     * This method sets up the recycler view to the posts for the page
     */
    private fun setupRecyclerView(posts: ArrayList<Post>) {
        //set the layout to linear and make the posts into a grid
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
    }


}