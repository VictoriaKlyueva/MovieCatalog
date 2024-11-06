package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.EMPTY_STRING
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    movie: MovieDetailsModel,
    movieEnhanced: FilmSearchByFiltersResponse_items?
) {
    val context = LocalContext.current
    val isFavorite by viewModel.isFavorite.observeAsState(initial = false)
    println(movieEnhanced?.ratingKinopoisk)

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.dark))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = "Background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                painter = painterResource(id = R.drawable.shadow_bottom_png),
                contentDescription = "Bottom Shadow",
                contentScale = ContentScale.FillHeight
            )

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.dark_faded),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        (context as? Activity)?.finish()
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Icon back",
                    tint = Color.White,
                )
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .background(
                        brush = if (isFavorite) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.gradient_start),
                                    colorResource(id = R.color.gradient_end)
                                )
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.dark_faded),
                                    colorResource(id = R.color.dark_faded)
                                )
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .clickable {
                        if (isFavorite) {
                            viewModel.removeFavorite()
                        } else {
                            viewModel.addToFavorite()
                        }
                        viewModel.toggleFavorite()
                    }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Icon heart",
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 72.dp,
                    bottom = 24.dp
                )
                .clip(shape = RoundedCornerShape(16.dp))
        ) {
            item {
                Spacer(modifier = Modifier.height(400.dp))
            }

            item {
                TitleSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FriendsSection()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                DescriptionSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                RatingSection(
                    movie,
                    movieEnhanced?.ratingKinopoisk,
                    movieEnhanced?.ratingImdb
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                InfoSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                DirectorSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                GenresSection(viewModel, movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FinanceSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ReviewSection(viewModel, movie.reviews)
            }
        }
    }
}