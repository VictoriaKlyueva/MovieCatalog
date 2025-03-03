package com.example.moviecatalog.presentation.view.FavoritesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.presentation.ui.*
import com.example.moviecatalog.presentation.viewModel.FavoritesViewModel
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.presentation.viewModel.factory.FavoritesViewModelFactory
import com.example.moviecatalog.presentation.viewModel.factory.SignInViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel

    private var favoritesGenres: List<GenreModel> by mutableStateOf(emptyList())
    private var favoriteMovies: List<MovieElementModel> by mutableStateOf(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewModel()

        return ComposeView(requireContext()).apply {
            setContent {
                Theme {
                    this@FavoritesFragment.Content()
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            FavoritesViewModelFactory(requireContext())
        )[FavoritesViewModel::class.java]
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        viewModel.fetchFavorites()

        viewModel.navigateToPlaceholder.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val navController = findNavController()
                navController.navigate(R.id.favoritesPlaceholderFragment)
                viewModel.resetNavigation()
            }
        }
    }

    private fun observeData() {
        viewModel.favoritesGenres.observe(viewLifecycleOwner) {
            favoritesGenres = it ?: emptyList()
        }
        viewModel.favoritesMovies.observe(viewLifecycleOwner) {
            favoriteMovies = it ?: emptyList()
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.dark))
                .padding(24.dp)
                .clickable {  },
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.favorite),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            if (viewModel.showGenres.value == true) {
                GenreSection(viewModel, favoritesGenres)
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (viewModel.showMovies.value == true) {
                MovieSection(favoriteMovies)
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}
