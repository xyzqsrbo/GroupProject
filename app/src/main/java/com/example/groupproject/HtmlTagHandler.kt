package com.example.groupproject

import android.text.Editable
import android.text.Html
import android.text.Spanned
import org.xml.sax.XMLReader

class HtmlTagHandler : Html.TagHandler {

    override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
        if (output == null) return

        when (tag) {
            "hr" -> handleHrTag(opening, output)
            // Handle other tags if needed
        }

    }

    private fun handleHrTag(opening: Boolean, output: Editable) {
        val placeholder = "\n-\n" // Makes sure the HR is drawn on a new line
        if (opening) {
            output.insert(output.length, placeholder)
        } else {
            output.setSpan(HrSpan(), output.length - placeholder.length, output.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}
