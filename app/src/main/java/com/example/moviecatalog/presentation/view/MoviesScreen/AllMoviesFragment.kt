package com.example.moviecatalog.presentation.view.MoviesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.databinding.FragmentAllMoviesBinding
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class AllMoviesFragment : Fragment() {
    private var _binding: FragmentAllMoviesBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private val viewModel: MoviesViewModel by viewModels()

    private var allMovies: List<MovieElementModel> = emptyList()
    private lateinit var allMoviesAdapter: AllMoviesAdapter

    private var currentPage = 2
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewFavorites.layoutManager = gridLayoutManager

        allMoviesAdapter = AllMoviesAdapter(emptyList(), viewModel)
        binding.recyclerViewFavorites.adapter = allMoviesAdapter

        binding.recyclerViewFavorites.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && hasReachedEndOfList(gridLayoutManager)) {
                    loadMoreMovies()
                }
            }
        })
    }

    private fun hasReachedEndOfList(layoutManager: GridLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        return visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
    }

    private fun loadMoreMovies() {
        isLoading = true
        currentPage++
        viewModel.fetchMovies(currentPage)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeMovies()

        viewModel.fetchMovies(currentPage)
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            allMoviesAdapter.updateMovies(allMovies + movies)
            allMovies = allMovies + movies

            isLoading = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
