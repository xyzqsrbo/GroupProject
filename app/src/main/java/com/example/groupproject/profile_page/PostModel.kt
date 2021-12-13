package com.example.groupproject.profile_page

import com.example.groupproject.R
import java.io.Serializable


data class Post(var picture: Int = R.drawable.ic_user_picture,
                var username: String,
                var postId: String):Serializable
