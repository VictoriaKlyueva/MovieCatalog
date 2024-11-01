package com.example.moviecatalog.presentation.view.FavoritesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.presentation.ui.*
import com.example.moviecatalog.R


@Composable
fun MovieSection(favoriteMovies: List<MovieElementModel>) {
    Text(
        modifier = GradientTextModifier.gradientTextModifier,
        text = stringResource(id = R.string.favorite_movies),
        style = MaterialTheme.typography.bodyLarge
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(favoriteMovies) { movie -> MovieItem(movie) }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MovieItem(movie: MovieElementModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.698f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = movie.name,
                modifier = Modifier.fillMaxSize()
            )

            val averageRating = movie.reviews.map { it.rating }.average()
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart),
                color = getRatingColor(averageRating),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = String.format("%.1f", averageRating),
                    style = MaterialTheme.typography.bodySmall,
                    color = getRatingTextColor(averageRating),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Text(
            text = "${movie.reviews.size} reviews",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

private fun getRatingColor(rating: Double): Color {
    return when {
        rating >= 9 -> nineStars
        rating >= 8 -> eightStars
        rating >= 7 -> sevenStars
        rating >= 6 -> sixStars
        rating >= 5 -> fiveStars
        rating >= 4 -> fourStars
        rating >= 3 -> threeStars
        rating >= 2 -> twoStars
        rating >= 1 -> oneStar
        else -> GrayFaded
    }
}

private fun getRatingTextColor(rating: Double): Color {
    return if (rating in 5.0..7.0)
        DarkFaded
    else
        Color.White
}

