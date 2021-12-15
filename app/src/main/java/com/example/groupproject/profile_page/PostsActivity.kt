package com.example.groupproject.profile_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 12, 2021
 */
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.showToast
import kotlinx.android.synthetic.main.profile_page.*

/**
 * This is the activity for posts
 * Don't think this is in use anymore need to look into it
 */
class PostsActivity : AppCompatActivity() {

    //Object for the TAG in log messaging
    companion object {
        val TAG: String = PostsActivity::class.java.simpleName
    }

    /**
     * This method sets the layout to the post activity
     * @Param savedInstanceState: the saved state of the page
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.PostInspect)
        showToast("Post was Clicked!")

        //setupRecyclerView()
    }

    /**
     * This function sets up the recycler view to load into the page
     */
    /*
    private fun setupRecyclerView(){
        //set the layout of the recycler view
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        //set the adapter to the recycler view
        val adapter = PostsAdapter(this, UserSupplier.user.posts)
        recyclerView.adapter = adapter
    }

     */
}