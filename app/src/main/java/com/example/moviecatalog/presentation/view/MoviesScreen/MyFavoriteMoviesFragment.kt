package com.example.moviecatalog.presentation.view.MoviesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.FragmentMyFavoritesBinding
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class MyFavoriteMoviesFragment : Fragment() {
    private var _binding: FragmentMyFavoritesBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private val moviesViewModel: MoviesViewModel by viewModels()

    private var favoritesMovies: List<MovieElementModel> = emptyList()
    private lateinit var moviesAdapter: MyFavoriteMoviesAdapter

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
        moviesViewModel.fetchFavoriteMovies()
        observeMovies()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MyFavoriteMoviesAdapter(emptyList())
        binding.recyclerViewFavorites.adapter = moviesAdapter
        binding.recyclerViewFavorites.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        binding.recyclerViewFavorites.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    updateFirstVisibleItem()
                }
            }
        )
    }

    private fun updateFirstVisibleItem() {
        val layoutManager = binding.recyclerViewFavorites.layoutManager as? LinearLayoutManager
        val firstCompletelyVisibleItemPosition =
            layoutManager?.findFirstCompletelyVisibleItemPosition()

        if (firstCompletelyVisibleItemPosition != null && firstCompletelyVisibleItemPosition >= 0) {
            moviesAdapter.setFirstVisiblePosition(firstCompletelyVisibleItemPosition)
        }
    }

    private fun observeMovies() {
        moviesViewModel.favoritesMovies.observe(viewLifecycleOwner) { movies ->
            favoritesMovies = movies
            moviesAdapter.updateMovies(movies)
            if (movies.isNotEmpty()) {
                binding.recyclerViewFavorites.scrollToPosition(0)
            } else {
                println("No movies available")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

