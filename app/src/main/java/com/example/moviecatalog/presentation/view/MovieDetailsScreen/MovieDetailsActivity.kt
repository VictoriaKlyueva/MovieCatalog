package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.common.Constants.EMPTY_STRING
import com.example.moviecatalog.common.Constants.MOVIE_ID
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.presentation.ui.Theme
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.presentation.viewModel.factory.MovieDetailsViewModelFactory
import com.example.moviecatalog.presentation.viewModel.factory.SignInViewModelFactory

class MovieDetailsActivity : ComponentActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private var favoritesGenres: List<GenreModel> by mutableStateOf(emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

        val movieId = intent.getStringExtra(MOVIE_ID) ?: EMPTY_STRING
        if (movieId.isNotEmpty()) {
            viewModel.fetchMovie(movieId)
        }

        observeData()

        viewModel.fetchFavoritesGenres()
    }

    private fun observeData() {
        viewModel.movie.observe(this) { movie ->
            if (movie != null) {
                viewModel.movieEnhanced.observe(this) { movieEnhanced ->
                    setContent {
                        Theme {
                            MovieDetailsScreen(viewModel, movie, movieEnhanced)
                        }
                    }
                }
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                println("Error fetching movie: $it")
            }
        }

        viewModel.movieEnhanced.observe(this) { error ->
            error?.let {
                println("Error fetching enhanced movie: $it")
            }
        }

        viewModel.favoritesGenres.observe(this) {
            favoritesGenres = it ?: emptyList()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(this)
        )[MovieDetailsViewModel::class.java]
    }
}
