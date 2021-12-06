package com.example.groupproject.profile_page

data class User(var username: String,
                var fName: String,
                var lName: String,
                var bio: String)

object UserSupplier{
    val user = User("ManTheDan", "Dan", "TheMan", "This is quite possibly one of the best profiles of all the profiles")
}