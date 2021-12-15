package com.example.groupproject.profile_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
import com.example.groupproject.R
import java.io.Serializable

/**
 * This class holds the data to the user object
 * Serializable to parse data into a new view
 */
data class User(
    var username: String = "",
    var fName: String = "",
    var lName: String = "",
    var bio: String = ""
) : Serializable

/**
 * Object made for testing purposes
 * created a test user
 */
object UserSupplier {
    val user = User(
        "ManTheDan",
        "Dan",
        "TheMan",
        "This is quite possibly one of the best profiles of all the profiles")
}