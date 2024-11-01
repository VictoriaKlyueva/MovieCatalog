package com.example.moviecatalog.presentation.view.FavoritesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.presentation.ui.*
import com.example.moviecatalog.presentation.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var favoritesGenres: List<GenreModel> by mutableStateOf(emptyList())
    private var favoriteMovies: List<MovieElementModel> by mutableStateOf(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Theme {
                    this@FavoritesFragment.Content()
                }
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
                .background(colorResource(id = R.color.dark))
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.favorite),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            GenreSection(favoritesGenres)

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            MovieSection(favoriteMovies)

            Spacer(
                modifier = Modifier.height(64.dp)
            )
        }
    }
}
