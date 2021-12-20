package com.example.groupproject.profile_page

/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.groupproject.R
import com.example.groupproject.search_page.SearchActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.profile_page.*
import java.io.File

/**
 * This class is the recycler view adapter to the posts
 * @param context: context of the page
 * @param posts: the list of posts being set to the page
 */

class PostsAdapter(val clickedItem: ClickedItem) :
    RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {
    var postList = ArrayList<Post>()

    fun setData(postList: ArrayList<Post>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    interface ClickedItem {
        fun clickedItem(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var post = postList[position]
        val imageName = post.postId
        val storageRef = FirebaseStorage.getInstance().reference.child("Images/$imageName")
        val localFile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.image.setImageBitmap(bitmap)
        }

        //holder.image.setImageResource(post.picture)
        holder.username.text = post.postId

        holder.itemView.setOnClickListener {
            clickedItem.clickedItem(post)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.imgPostMod0
        var username = itemView.txtPostMod0
    }
}


/*
class PostsAdapter(val context: Context, private val posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {
    companion object {
        val TAG: String = PostsAdapter::class.java.simpleName
    }

    /**
     * This method creates the holder for the view
     * @param parent: the parent view that the view is going into
     * @param viewType: the type of view that the view is going into
     * @return: returns the view holder for the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post, parent, false)
        return MyViewHolder(view)
    }

    /**
     * This method returns the size of the post list
     * @return: the size of the posts list
     */
    override fun getItemCount(): Int {
        return posts.size
    }

    /**
     * This method sets the binded view holder
     * @param holder: the view holder
     * @param position: the position of the index for the post list
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.setData(post, position)
    }

    /**
     * This class sets all the data and click listeners for the data in the recylcer view
     * @param itemView: the view
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentPost: Post? = null
        var currentPosition: Int = 0

        //set the on click listener for a specific post
        init {
            itemView.setOnClickListener {
                currentPost?.let {
                    Log.i(TAG, currentPost!!.postId + " Clicked!")
                    Toast.makeText(context, currentPost!!.postId + " Clicked!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@PostsAdapter, SearchActivity::class.java))
                }
            }
        }

        /**
         * this method sets the data to the post view
         * @param post: the post that is being set
         * @param pos: the position of the index in the array list
         */
        fun setData(post: Post?, pos: Int) {
            //if there is a post set the image and text
            post?.let {
                itemView.imgPostMod0.setImageResource(post.picture)
                itemView.txtPostMod0.text = post.username
            }
            //update the indexes
            this.currentPost = post
            this.currentPosition = pos
        }
    }
}

 */


