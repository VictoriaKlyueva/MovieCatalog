package com.example.moviecatalog.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.presentation.ui.*
import com.example.moviecatalog.presentation.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var favoritesGenres: List<GenreModel> by mutableStateOf(emptyList())
    private var favoriteMovies: List<MovieElementModel> by mutableStateOf(emptyList())

    private val gradientTextModifier = Modifier
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            val brush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Theme {
                    this@FavoritesFragment.Content()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        favoritesViewModel.fetchFavoritesGenres()
        favoritesViewModel.fetchFavoritesMovies()
    }

    private fun observeData() {
        favoritesViewModel.favoritesGenres.observe(viewLifecycleOwner) {
            favoritesGenres = it ?: emptyList()
        }
        favoritesViewModel.favoritesMovies.observe(viewLifecycleOwner) {
            favoriteMovies = it ?: emptyList()
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = stringResource(id = R.string.favorite), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(32.dp))
            GenreSection()
            Spacer(modifier = Modifier.height(32.dp))
            MovieSection()
        }
    }

    @Composable
    private fun GenreSection() {
        Text(modifier = gradientTextModifier, text = stringResource(id = R.string.favorite_genres), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(favoritesGenres) { genre ->
                GenreItem(genre.name.replaceFirstChar(Char::uppercaseChar))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    private fun MovieSection() {
        Text(modifier = gradientTextModifier, text = stringResource(id = R.string.favorite_movies), style = MaterialTheme.typography.bodyLarge)
        LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxWidth()) {
            items(favoriteMovies) { movie -> MovieItem(movie) }
        }
    }

    @Composable
    fun GenreItem(genre: String) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DarkFaded),
            verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier.padding(start = 16.dp).weight(1f), text = genre, style = MaterialTheme.typography.bodyMedium)

            Button(
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(resources.getColor(R.color.dark))),
                shape = RoundedCornerShape(8.dp),
                onClick = { /* Логика удаления из избранного */ }
            ) {
                Image(painter = painterResource(id = R.drawable.ic_broken_heart), contentDescription = "Broken Heart", modifier = Modifier.size(24.dp))
            }
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
                Image(painter = rememberAsyncImagePainter(movie.poster), contentDescription = movie.name, modifier = Modifier.fillMaxSize())
                val averageRating = movie.reviews.map { it.rating }.average()
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    color = getRatingColor(averageRating),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(text = String.format("%.1f", averageRating), style = MaterialTheme.typography.bodySmall, color = getRatingTextColor(averageRating), modifier = Modifier.padding(4.dp))
                }
            }
            Text(text = "${movie.reviews.size} reviews", style = MaterialTheme.typography.bodySmall, color = Color.White)
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
        return if (rating in 5.0..7.0) DarkFaded else Color.White
    }
}
