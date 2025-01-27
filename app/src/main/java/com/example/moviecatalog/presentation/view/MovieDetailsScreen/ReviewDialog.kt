package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.REVIEW_TEXT_CANNOT_BE_EMPTY
import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.presentation.view.Components.GradientSwitch
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(
    viewModel: MovieDetailsViewModel,
    showDialog: Boolean,
    hasReview: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var sliderValue by remember { mutableFloatStateOf(5f) }
    var switchState by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    if (!showDialog) return

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.dark),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = if (hasReview)
                    stringResource(id = R.string.edit_review)
                else
                    stringResource(id = R.string.add_review),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.score),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            CustomSlider(
                sliderValue = sliderValue,
                onValueChange = { sliderValue = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                cursorBrush = SolidColor(colorResource(id = R.color.gray)),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.dark_faded),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.review_text),
                                color = colorResource(id = R.color.gray_faded),
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        innerTextField()
                    }
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = colorResource(id = R.color.gray),
                    fontSize = 16.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.anonymous_review),
                    color = colorResource(R.color.gray),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                GradientSwitch(
                    checked = switchState,
                    onCheckedChange = { switchState = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(40.dp)
                    .align(Alignment.End)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                colorResource(id = R.color.gradient_start),
                                colorResource(id = R.color.gradient_end)
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
                onClick = {
                    if (textFieldValue.text.isEmpty())
                        Toast.makeText(
                            context,
                            REVIEW_TEXT_CANNOT_BE_EMPTY,
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        val review = ReviewModifyModel(
                            rating = sliderValue.toInt(),
                            reviewText = textFieldValue.text,
                            isAnonymous = switchState
                        )

                        if (hasReview) {
                            viewModel.editReview(review)
                        } else {
                            viewModel.addReview(review)
                        }

                        onDismiss()
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.send),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }

        }
    }
}

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
                        val positionFraction = i / 8.5f
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .offset(x = with(LocalDensity.current) {
                                    (positionFraction * 235).dp
                                } - 125.dp)
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

        // Indicator
        val thumbPosition = with(LocalDensity.current) { (sliderValue / 10f * 265).dp }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .offset(y = (-50).dp)
                .align(Alignment.Center)
                .offset(x = thumbPosition - 132.dp)
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
