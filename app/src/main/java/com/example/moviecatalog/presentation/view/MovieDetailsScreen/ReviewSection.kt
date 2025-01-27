@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.moviecatalog.common.Constants.REVIEW_TEXT_CANNOT_BE_EMPTY
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
        Row(
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
            Row(
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
                            contentDescription = "Delete Review",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
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

        // Show Review Dialog
        if (showDialog) {
            ReviewDialog(
                viewModel = viewModel,
                showDialog = showDialog,
                hasReview = hasReview,
                onDismiss = { showDialog = false }
            )
        }
    }
}