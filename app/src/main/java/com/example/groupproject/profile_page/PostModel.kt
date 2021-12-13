package com.example.groupproject.profile_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
import com.example.groupproject.R
import java.io.Serializable

/**
 * This class is the data holder for a post object
 */
data class Post(var picture: Int = R.drawable.ic_user_picture,
                var username: String,
                var postId: String):Serializable
