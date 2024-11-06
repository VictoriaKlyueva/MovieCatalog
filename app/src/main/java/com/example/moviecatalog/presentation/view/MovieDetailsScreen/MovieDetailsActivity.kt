package com.example.moviecatalog.presentation.view.MovieDetailsScreen


import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.EMPTY_STRING
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.presentation.common.Constants.MOVIE_ID
import com.example.moviecatalog.presentation.ui.Theme
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

class MovieDetailsActivity : ComponentActivity() {

    private lateinit var viewModel: MovieDetailsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        val movieId = intent.getStringExtra(MOVIE_ID) ?: EMPTY_STRING
        if (movieId.isNotEmpty()) {
            viewModel.fetchMovie(movieId)
        }

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
    }
}
