package com.example.groupproject

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

class HrSpan : ReplacementSpan() {


    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?) = 0

    override fun draw(
        canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int,
        bottom: Int, paint: Paint
    ) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f

        // Draw line in the middle of the available space
        val middle = ((top + bottom) / 2).toFloat()

        canvas.drawLine(0f, middle, canvas.width.toFloat(), middle, paint)
    }

}