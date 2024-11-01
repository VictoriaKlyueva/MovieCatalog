package com.example.moviecatalog.presentation.view.FavoritesScreen

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import com.example.moviecatalog.presentation.ui.GradientEnd
import com.example.moviecatalog.presentation.ui.GradientStart

object GradientTextModifier {
    val gradientTextModifier = Modifier
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            val brush = Brush.horizontalGradient(
                listOf(
                    GradientStart,
                    GradientEnd
                )
            )
            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }
}