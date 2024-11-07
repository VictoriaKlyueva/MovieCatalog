package com.example.moviecatalog.presentation.view.FavoritesScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.MOVIE_ID
import com.example.moviecatalog.presentation.view.MovieDetailsScreen.MovieDetailsActivity


@Composable
fun MovieSection(favoriteMovies: List<MovieElementModel>) {
    Text(
        modifier = getGradientTextModifier(),
        text = stringResource(id = R.string.favorite_movies),
        style = MaterialTheme.typography.bodyLarge
    )

    Spacer(modifier = Modifier.height(16.dp))

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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.698f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                goToMovieScreen(movie.id, context)
            },
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

@Composable
private fun getRatingColor(rating: Double): Color {
    return when {
        rating >= 9 -> colorResource(id = R.color.nine_stars)
        rating >= 8 -> colorResource(id = R.color.eight_stars)
        rating >= 7 -> colorResource(id = R.color.seven_stars)
        rating >= 6 -> colorResource(id = R.color.six_stars)
        rating >= 5 -> colorResource(id = R.color.five_stars)
        rating >= 4 -> colorResource(id = R.color.four_stars)
        rating >= 3 -> colorResource(id = R.color.three_stars)
        rating >= 2 -> colorResource(id = R.color.two_stars)
        rating >= 1 -> colorResource(id = R.color.one_star)
        else -> colorResource(id = R.color.gray_faded)
    }
}

@Composable
private fun getRatingTextColor(rating: Double): Color {
    return if (rating in 5.0..7.0)
        colorResource(id = R.color.dark_faded)
    else
        Color.White
}

private fun goToMovieScreen(movieId: String, context: Context) {
    val intent = Intent(context, MovieDetailsActivity::class.java)
    intent.putExtra(MOVIE_ID, movieId)
    context.startActivity(intent)
}