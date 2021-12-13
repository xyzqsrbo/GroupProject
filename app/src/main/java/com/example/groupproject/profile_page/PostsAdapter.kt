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
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post.view.*
import android.widget.Toast
import com.example.groupproject.R

/**
 * This class is the recycler view adapter to the posts
 * @param context: context of the page
 * @param posts: the list of posts being set to the page
 */
class PostsAdapter(val context: Context, private val posts: List<Post>) :
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
