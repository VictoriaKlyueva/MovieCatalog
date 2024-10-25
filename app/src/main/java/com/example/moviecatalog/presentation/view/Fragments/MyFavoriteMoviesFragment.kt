package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.FragmentMyFavoritesBinding
import com.example.moviecatalog.presentation.view.Adapters.MyFavoriteMoviesPagerAdapter
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class MyFavoriteMoviesFragment : Fragment() {
    private var _binding: FragmentMyFavoritesBinding? = null
    private val binding get() = _binding!!

    private val moviesViewModel: MoviesViewModel by viewModels()

    private var favoritesMovies: List<MovieElementModel> = emptyList()
    private lateinit var moviesAdapter: MyFavoriteMoviesPagerAdapter
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            if (::moviesAdapter.isInitialized && moviesAdapter.itemCount > 0) {
                val currentItem = binding.viewPagerFavorites.currentItem
                val nextItem = if (currentItem == moviesAdapter.itemCount - 1)
                    0
                else
                    currentItem + 1

                binding.viewPagerFavorites.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 5000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFavoritesBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeMovies()
        moviesViewModel.fetchFavoriteMovies()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MyFavoriteMoviesPagerAdapter(emptyList())
        binding.viewPagerFavorites.adapter = moviesAdapter
        handler.postDelayed(runnable, 3000)
    }

    private fun observeMovies() {
        moviesViewModel.favoritesMovies.observe(viewLifecycleOwner) { movies ->
            favoritesMovies = movies
            moviesAdapter.updateMovies(movies)
            if (movies.isNotEmpty()) {
                binding.viewPagerFavorites.setCurrentItem(0, false)
            } else {
                println("нет фильмов")
            }
        }
    }

    override fun onDestroyView() {
        handler.removeCallbacks(runnable)
        _binding = null
        super.onDestroyView()
    }
}
