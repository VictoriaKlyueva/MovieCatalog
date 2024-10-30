package com.example.moviecatalog.presentation.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

val manropeFamily = FontFamily(
    Font(R.font.manrope_light, FontWeight.Light),
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val Typography = androidx.compose.material3.Typography(
    bodyMedium = TextStyle(
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White
    ),
    bodyLarge = TextStyle(
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = manropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    )
)