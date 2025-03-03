package com.example.moviecatalog.presentation.view.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R

private fun calculateThumbOffset(
    start: Float,
    stop: Float,
    fraction: Float
): Float = start + (stop - start) * fraction

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GradientSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedTrackColor: Brush = Brush.horizontalGradient(
        listOf(
            colorResource(id = R.color.gradient_start),
            colorResource(id = R.color.gradient_end)
        )
    ),
    uncheckedTrackColor: Brush = Brush.horizontalGradient(
        listOf(
            colorResource(id = R.color.dark_faded),
            colorResource(id = R.color.dark_faded)
        )
    ),
    thumbColor: Color = Color.White
) {
    val thumbPosition by animateFloatAsState(targetValue = if (checked) 1f else 0f)
    val circleRadius = remember { 10.dp }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(width = 51.dp, height = 31.dp)
            .background(color = Color.Transparent)
            .clickable(
                onClick = { onCheckedChange(!checked) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val trackBrush = if (checked) checkedTrackColor else uncheckedTrackColor

            drawRoundRect(
                brush = trackBrush,
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(x = 18.dp.toPx(), y = 18.dp.toPx())
            )

            val thumbOffset = calculateThumbOffset(
                start = 16.dp.toPx(),
                stop = size.width - 16.dp.toPx(),
                fraction = thumbPosition
            )

            drawCircle(
                color = thumbColor,
                radius = circleRadius.toPx(),
                center = Offset(x = thumbOffset, y = size.height / 2)
            )
        }
    }
}