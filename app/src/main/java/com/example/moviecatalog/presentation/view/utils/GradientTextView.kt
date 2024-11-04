package com.example.moviecatalog.presentation.view.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.moviecatalog.R

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    private val gradientColors = intArrayOf(
        resources.getColor(R.color.gradient_start),
        resources.getColor(R.color.gradient_end)
    )

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val width = paint.measureText(text.toString())
        val shader: Shader = LinearGradient(
            0f, 0f, width, textSize,
            gradientColors,
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
        super.onDraw(canvas)
        paint.shader = null
    }
}
