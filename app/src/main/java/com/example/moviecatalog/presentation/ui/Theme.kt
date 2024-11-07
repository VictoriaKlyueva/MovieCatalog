package com.example.moviecatalog.presentation.ui

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.res.colorResource
import com.example.moviecatalog.R

@Composable
fun getColorScheme(): ColorScheme {
    return lightColorScheme(
        primary = colorResource(id = R.color.dark),
        secondary = colorResource(id = R.color.dark_faded),
        tertiary = colorResource(id = R.color.gray_faded)
    )
}

@Composable
fun Theme(
    content: @Composable () -> Unit,
) {
    val colorScheme = getColorScheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}