package com.example.groupproject.profile_page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupproject.PostSupplier
import com.example.groupproject.showToast
import kotlinx.android.synthetic.main.profile_page.*

class PostsActivity : AppCompatActivity() {

    companion object {
        val TAG: String = PostsActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.PostInspect)
        showToast("Post was Clicked!")

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val adapter = PostsAdapter(this, PostSupplier.post)
        recyclerView.adapter = adapter
    }
}