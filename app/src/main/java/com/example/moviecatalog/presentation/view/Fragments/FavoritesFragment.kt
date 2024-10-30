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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.moviecatalog.presentation.ui.DarkFaded
import com.example.moviecatalog.presentation.ui.GradientEnd
import com.example.moviecatalog.presentation.ui.GradientStart
import com.example.moviecatalog.presentation.ui.GrayFaded
import com.example.moviecatalog.presentation.ui.Theme
import com.example.moviecatalog.presentation.ui.eightStars
import com.example.moviecatalog.presentation.ui.fiveStars
import com.example.moviecatalog.presentation.ui.fourStars
import com.example.moviecatalog.presentation.ui.nineStars
import com.example.moviecatalog.presentation.ui.oneStar
import com.example.moviecatalog.presentation.ui.sevenStars
import com.example.moviecatalog.presentation.ui.sixStars
import com.example.moviecatalog.presentation.ui.threeStars
import com.example.moviecatalog.presentation.ui.twoStars
import com.example.moviecatalog.presentation.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var favoritesGenres: List<GenreModel> = emptyList()
    private var favoriteMovies: List<MovieElementModel> = emptyList()

    private val gradientTextModifier =
        Modifier.graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            val brush = Brush.horizontalGradient(
                listOf(
                    GradientStart,
                    GradientEnd
                )
            )
            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                this@FavoritesFragment.Content()
            }
        }
    }

    private fun observeGenres() {
        favoritesViewModel.favoritesGenres.observe(viewLifecycleOwner) { result ->
            when {
                result.isNullOrEmpty() -> {
                    println("No genres found")
                    // Показать заглушку
                }
                else -> {
                    favoritesGenres = result
                }
            }
        }
    }

    private fun observeMovies() {
        favoritesViewModel.favoritesMovies.observe(viewLifecycleOwner) { result ->
            when {
                result.isNullOrEmpty() -> {
                    println("No genres found")
                    // Показать заглушку
                }
                else -> {
                    favoriteMovies = result
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGenres()
        observeMovies()

        favoritesViewModel.fetchFavoritesGenres()
        favoritesViewModel.fetchFavoritesMovies()
    }

    @Composable
    private fun Content() {
        val favoritesGenresState = remember { mutableStateOf(emptyList<GenreModel>()) }
        val favoritesMoviesState = remember { mutableStateOf(emptyList<MovieElementModel>()) }

        favoritesViewModel.favoritesGenres.observe(viewLifecycleOwner) { result ->
            favoritesGenresState.value = result ?: emptyList()
        }

        favoritesViewModel.favoritesMovies.observe(viewLifecycleOwner) { result ->
            favoritesMoviesState.value = result ?: emptyList()
        }

        return Theme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.favorite),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = gradientTextModifier,
                    text = stringResource(id = R.string.favorite_genres),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(favoritesGenresState.value) { genre ->
                        GenreItem(genre.name.replaceFirstChar(Char::uppercaseChar))
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = gradientTextModifier,
                    text = stringResource(id = R.string.favorite_movies),
                    style = MaterialTheme.typography.bodyLarge
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(favoritesMoviesState.value) { movie ->
                        MovieItem(movie)
                    }
                }
            }
        }
    }

    @Composable
    fun GenreItem(genre: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(DarkFaded),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                text = genre,
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(resources.getColor(R.color.dark))
                ),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    /* Логика удаления из избранного */
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_heart),
                    contentDescription = "Broken Heart",
                    modifier = Modifier.size(24.dp)
                )
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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.poster),
                    contentDescription = movie.name,
                    modifier = Modifier.fillMaxSize()
                )

                // Плашка с рейтингом
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    // color = Color.Black.copy(alpha = 0.7f),
                    color = when {
                        movie.reviews.map { it.rating }.average() >= 9 ->
                            nineStars
                        movie.reviews.map { it.rating }.average() >= 8 ->
                            eightStars
                        movie.reviews.map { it.rating }.average() >= 7 ->
                            sevenStars
                        movie.reviews.map { it.rating }.average() >= 6 ->
                            sixStars
                        movie.reviews.map { it.rating }.average() >= 5 ->
                            fiveStars
                        movie.reviews.map { it.rating }.average() >= 4 ->
                            fourStars
                        movie.reviews.map { it.rating }.average() >= 3 ->
                            threeStars
                        movie.reviews.map { it.rating }.average() >= 2 ->
                            twoStars
                        movie.reviews.map { it.rating }.average() >= 1 ->
                            oneStar
                        else ->
                            GrayFaded
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = String.format("%.1f", movie.reviews.map { it.rating }.average()),
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            movie.reviews.map { it.rating }.average() in (5.0 .. 7.0) ->
                                DarkFaded
                            else ->
                                Color.White
                        },
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

}
