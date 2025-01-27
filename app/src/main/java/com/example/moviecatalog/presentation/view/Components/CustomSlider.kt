package com.example.moviecatalog.presentation.view.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    sliderValue: Float,
    onValueChange: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        contentAlignment = Alignment.Center
    ) {
        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            value = sliderValue,
            onValueChange = onValueChange,
            valueRange = 0f..10f,
            steps = 9,
            colors = SliderDefaults.colors(
                activeTrackColor = colorResource(id = R.color.gradient_end),
                inactiveTrackColor = colorResource(id = R.color.dark_faded)
            ),
            thumb = {
                Box(
                    Modifier
                        .width(10.dp)
                        .height(44.dp)
                        .padding(start = 8.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    colorResource(id = R.color.gradient_start),
                                    colorResource(id = R.color.gradient_end)
                                )
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            },
            track = { sliderState ->
                val fraction by remember {
                    derivedStateOf {
                        (sliderState.value - sliderState.valueRange.start) /
                                (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                    }
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(fraction)
                            .align(Alignment.CenterStart)
                            .height(16.dp)
                            .padding(end = 8.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        colorResource(id = R.color.gradient_start),
                                        colorResource(id = R.color.gradient_end)
                                    )
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(1f - fraction)
                            .align(Alignment.CenterEnd)
                            .height(16.dp)
                            .padding(start = 6.dp)
                            .background(
                                colorResource(id = R.color.dark_faded),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )

                    // Adding points
                    for (i in 0..9) {
                        val positionFraction = with(LocalDensity.current) { i / 8.5f }
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .offset(x = with(LocalDensity.current) {
                                    (positionFraction * 235 - 125).dp
                                } )
                                .align(Alignment.Center)
                                .background(
                                    brush =
                                    if (sliderState.value >= positionFraction * 8.5 && sliderValue != 0f) {
                                        Brush.horizontalGradient(
                                            listOf(
                                                colorResource(R.color.white),
                                                colorResource(R.color.white)
                                            )
                                        )
                                    } else {
                                        Brush.horizontalGradient(
                                            listOf(
                                                colorResource(R.color.gradient_start),
                                                colorResource(R.color.gradient_end)
                                            )
                                        )
                                    },
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        )

        val thumbPosition = with(LocalDensity.current) { (sliderValue / 10f * 265).dp }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .offset(y = with(LocalDensity.current) { (-50).dp } )
                .align(Alignment.Center)
                .offset(x = with(LocalDensity.current) { thumbPosition - 132.dp } )
                .padding(horizontal = 36.dp, vertical = 4.dp)
                .background(
                    colorResource(id = R.color.dark_faded),
                    shape = CircleShape
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                text = sliderValue.toInt().toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}