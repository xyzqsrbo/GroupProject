package com.example.groupproject.profile_page

import com.example.groupproject.R
import java.io.Serializable

data class User(
    var username: String = "",
    var fName: String = "",
    var lName: String = "",
    var bio: String = "",
    var posts: List<Post>
) : Serializable

object UserSupplier {
    val user = User(
        "ManTheDan",
        "Dan",
        "TheMan",
        "This is quite possibly one of the best profiles of all the profiles",
        listOf(
            Post(R.drawable.ic_user_picture, "ManTheDan", "MandTheDan0"),
            Post(R.drawable.ic_downvote, "ManTheDan", "ManTheDan1"),
            Post(R.drawable.ic_upvote, "ManTheDan", "ManTheDan2"),
            Post(R.drawable.ic_launcher_background, "ManTheDan", "ManTheDan3"),
            Post(R.drawable.goku, "ManTheDan", "ManTheDan4"),
            Post(R.drawable.bert, "ManTheDan", "ManTheDan5"),
            Post(R.drawable.pixel_star, "ManTheDan", "ManTheDan6"),
            Post(R.drawable.ic_user_picture, "ManTheDan", "ManTheDan7"),
            Post(R.drawable.ic_add_friend, "ManTheDan", "ManTheDan8"),
            Post(R.drawable.ic_setting, "ManTheDan", "ManTheDan9")
        )
    )

}