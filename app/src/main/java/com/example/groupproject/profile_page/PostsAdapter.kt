package com.example.groupproject.profile_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import com.example.groupproject.Post
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post.view.*
import android.widget.Toast
import com.example.groupproject.R


class PostsAdapter(val context: Context, private val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {
    companion object {
        val TAG: String = PostsAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.setData(post, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentPost: Post? = null
        var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                currentPost?.let {
                    //context.showToast(currentPost!!.postId + " Clicked!")
                    Log.i(TAG, currentPost!!.postId + " Clicked!")
                    Toast.makeText(context, currentPost!!.postId + " Clicked!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun setData(post: Post?, pos: Int) {
            post?.let {
                itemView.imgPostMod0.setImageResource(post.picture)
                itemView.txtPostMod0.text = post.username
            }
            this.currentPost = post
            this.currentPosition = pos
        }
    }
}
