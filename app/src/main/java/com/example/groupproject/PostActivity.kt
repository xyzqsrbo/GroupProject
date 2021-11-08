package com.example.groupproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.HtmlCompat

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        // I want to have an <hr> tag below the location and description.
        val locationTextView: TextView = findViewById(R.id.locationView)
        val locationText = getString(R.string.location_of_the_image)
        val tagHandler = HtmlTagHandler()
        locationTextView.text = HtmlCompat.fromHtml(locationText, HtmlCompat.FROM_HTML_MODE_LEGACY, null, tagHandler)
        /*
        val html = "Hello <br> hai<br> I am fine <hr> Another line here <hr><hr>"
        val tagHandler = HtmlTagHandler()

        textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY, null, tagHandler)
         */
    }

}

