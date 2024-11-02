package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.presentation.ui.Theme
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

class MovieDetailsActivity : ComponentActivity() {

    private val movieDetailsViewModel = MovieDetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieDetailsViewModel.randomMovie.observe(this) { randomMovie ->
            if (randomMovie != null) {
                setContent {
                    Theme {
                        MovieDetailsScreen(randomMovie)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(movie: MovieElementModel) {
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

            // Иконка назад
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
                        /* Обработчик нажатия */
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

            // Плашка с сердечком
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.dark_faded),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
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
                DescriptionSection()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                RatingSection()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                InfoSection()
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
                GenresSection(movie)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FinanceSection()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ReviewSection()
            }
        }
    }
}
