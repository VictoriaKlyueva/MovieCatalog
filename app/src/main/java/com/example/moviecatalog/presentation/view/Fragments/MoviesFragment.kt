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
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.presentation.view.Adapters.MoviesAdapter
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var movieList: List<MovieElementModel> = emptyList()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeMovies()

        binding.RandomMovieButton.setOnClickListener {
            onRandomMovieButtonClicked()
        }

        moviesViewModel.fetchMovies(page = 1)
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter(emptyList()) { movie ->
            onWatchButtonClicked(movie)
        }
        binding.viewPager.adapter = moviesAdapter

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPager.currentItem
                val nextItem = if (currentItem == moviesAdapter.itemCount - 1) 0 else currentItem + 1
                binding.viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 5000)
            }
        }

        handler.postDelayed(runnable, 3000)
    }

    private fun observeMovies() {
        binding.progressBar.visibility = View.VISIBLE

        moviesViewModel.movies.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE

            when {
                result.isNullOrEmpty() -> {
                    println("No movies found")
                }
                else -> {
                    moviesAdapter.updateMovies(result)
                    movieList = result
                }
            }
        }
    }

    private fun onRandomMovieButtonClicked() {
        val randomMovie = moviesViewModel.getRandomMovie(movieList)
        println("Случайный фильм: ${randomMovie.name}")
    }

    private fun onWatchButtonClicked(movie: MovieElementModel) {
        println("Представим что MovieDetailsActivity запустилась")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

