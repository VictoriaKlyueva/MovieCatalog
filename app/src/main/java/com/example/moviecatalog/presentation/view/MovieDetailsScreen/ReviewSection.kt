@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.moviecatalog.common.Constants
import com.example.moviecatalog.data.model.main.ReviewModel
import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.presentation.view.Components.GradientSwitch
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewSection(viewModel: MovieDetailsViewModel, reviews: List<ReviewModel>) {
    var currentReviewIndex by remember { mutableIntStateOf(0) }

    var hasReview by remember { mutableStateOf(false) }
    viewModel.findUserReview { reviewId ->
        hasReview = reviewId != null
    }

    var showDialog by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0f) }
    var switchState by remember { mutableStateOf(false) }

    val currentReview =
        if (reviews.isNotEmpty())
            reviews[min(currentReviewIndex, reviews.size - 1)]
        else
            null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.dark_faded),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        // Header
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                tint = colorResource(id = R.color.gray),
                painter = painterResource(id = R.drawable.ic_review),
                contentDescription = "Icon heart",
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = stringResource(id = R.string.reviews),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display Review
        currentReview?.let {
            Review(viewModel, it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Buttons
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            // Add review
            Row (
                modifier = Modifier
                    .weight(1f)
            ) {
                if (hasReview) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
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
                        contentPadding = PaddingValues(),
                        onClick = {
                            showDialog = true
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit_review),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        modifier = Modifier
                            .size(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            viewModel.deleteReview()
                            hasReview = false
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_trash),
                            tint = Color.Unspecified,
                            contentDescription = "Broken Heart",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
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
                        contentPadding = PaddingValues(),
                        onClick = {
                            showDialog = true
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_review),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false }
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

                        Spacer(modifier = Modifier.height(8.dp))

                        Slider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp),
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                            valueRange = 0f..9f,
                            steps = 9,
                            colors = SliderDefaults.colors(
                                activeTrackColor = colorResource(id = R.color.gradient_end),
                                inactiveTrackColor = colorResource(id = R.color.dark_faded)
                            ),
                            thumb = {
                                Box(
                                    Modifier
                                        .width(2.dp)
                                        .height(44.dp)
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
                                            .padding(end = 16.dp)
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
                                            .padding(start = 2.dp)
                                            .background(
                                                colorResource(id = R.color.dark_faded),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
                        var isFocused by remember { mutableStateOf(false) }

                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                    if (isFocused && textFieldValue.text.isEmpty()) {
                                        textFieldValue = TextFieldValue(Constants.EMPTY_STRING)
                                    }
                                },
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
                                    if (!isFocused && textFieldValue.text.isEmpty()) {
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
                                val review = ReviewModifyModel(
                                    rating = sliderValue.toInt() + 1,
                                    reviewText = textFieldValue.text,
                                    isAnonymous = switchState
                                )

                                if (hasReview) {
                                    viewModel.editReview(review)
                                }
                                else {
                                    viewModel.addReview(review)
                                    hasReview = true
                                }

                                showDialog = false
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

            Spacer(modifier = Modifier.width(24.dp))

            // Button previous
            Button(
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    if (currentReviewIndex > 0)
                        colorResource(R.color.dark)
                    else
                        Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(),
                onClick = {
                    if (currentReviewIndex > 0) {
                        currentReviewIndex--
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(id = R.drawable.ic_back),
                    tint = Color.White,
                    contentDescription = "Icon back"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Button next
            Button(
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(
                    if (currentReviewIndex < reviews.size - 1)
                        colorResource(R.color.dark)
                    else
                        Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(),
                onClick = {
                    if (currentReviewIndex < reviews.size - 1) {
                        currentReviewIndex++
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(id = R.drawable.ic_next),
                    tint = Color.White,
                    contentDescription = "Icon next"
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Review(viewModel: MovieDetailsViewModel, review: ReviewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        User(viewModel, review, review.author)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = review.reviewText ?: "No review text available",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun User(viewModel: MovieDetailsViewModel, review: ReviewModel, author: UserShortModel?) {

    val dateHelper = remember { DateHelper() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            ),
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .clickable {
                    author?.let { viewModel.addFriend(it) }
                },
            painter = rememberImagePainter(
                data = author?.avatar ?: R.drawable.avatar_default,
                builder = {
                    error(R.drawable.avatar_default)
                }
            ),
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = author?.nickName ?: stringResource(id = R.string.nick_name_default),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )

            Text(
                text = dateHelper.convertFromDateTimezonesSeconds(review.createDateTime),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }

        val backgroundColor = when (review.rating) {
            1 -> colorResource(id = R.color.one_star)
            2 -> colorResource(id = R.color.two_stars)
            3 -> colorResource(id = R.color.three_stars)
            4 -> colorResource(id = R.color.four_stars)
            5 -> colorResource(id = R.color.five_stars)
            6 -> colorResource(id = R.color.six_stars)
            7 -> colorResource(id = R.color.seven_stars)
            8 -> colorResource(id = R.color.eight_stars)
            9 -> colorResource(id = R.color.nine_stars)
            10 -> colorResource(id = R.color.nine_stars)
            else -> colorResource(id = R.color.dark_faded)
        }

        Row(
            modifier = Modifier
                .height(32.dp)
                .wrapContentSize()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 8.dp, top = 6.dp, bottom = 6.dp)
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.ic_star),
                tint = Color.White,
                contentDescription = "Icon star",
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        top = 6.dp,
                        bottom = 6.dp,
                        end = 8.dp
                    ),
                text = review.rating.toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}