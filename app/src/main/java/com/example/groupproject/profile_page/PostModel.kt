package com.example.groupproject

import com.example.groupproject.profile_page.UserSupplier

data class Post(var picture: Int,
                var username: String,
                var postId: String)

object PostSupplier {
    val post = listOf(
        Post(R.drawable.ic_user_picture, UserSupplier.user.username, "MandTheDan0"),
        Post(R.drawable.ic_downvote, UserSupplier.user.username, "ManTheDan1"),
        Post(R.drawable.ic_upvote, UserSupplier.user.username, "ManTheDan2"),
        Post(R.drawable.ic_launcher_background, UserSupplier.user.username, "ManTheDan3"),
        Post(R.drawable.goku, UserSupplier.user.username, "ManTheDan4"),
        Post(R.drawable.bert, UserSupplier.user.username, "ManTheDan5"),
        Post(R.drawable.pixel_star, UserSupplier.user.username, "ManTheDan6"),
        Post(R.drawable.ic_user_picture, UserSupplier.user.username, "ManTheDan7"),
        Post(R.drawable.ic_add_friend, UserSupplier.user.username, "ManTheDan8"),
        Post(R.drawable.ic_setting, UserSupplier.user.username, "ManTheDan9")
    )
}