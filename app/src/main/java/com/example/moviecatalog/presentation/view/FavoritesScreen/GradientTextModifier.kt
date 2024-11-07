package com.example.moviecatalog.presentation.view.FavoritesScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import com.example.moviecatalog.R

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
fun getGradientTextModifier(): Modifier {
    val gradientStart = colorResource(id = R.color.gradient_start)
    val gradientEnd = colorResource(id = R.color.gradient_end)

    return Modifier
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            val brush = Brush.horizontalGradient(
                listOf(gradientStart, gradientEnd)
            )

            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }
}

