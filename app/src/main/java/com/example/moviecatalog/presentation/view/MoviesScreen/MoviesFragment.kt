package com.example.moviecatalog.presentation.view.MoviesScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.presentation.view.MovieDetailsScreen.MovieDetailsActivity
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

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
        moviesAdapter = MoviesAdapter(emptyList())
        binding.viewPager.adapter = moviesAdapter

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (_binding == null)
                    return

                val currentItem = binding.viewPager.currentItem
                val nextItem =
                    if (currentItem == moviesAdapter.itemCount - 1)
                        0
                    else
                        currentItem + 1

                binding.viewPager.setCurrentItem(nextItem, true)
                updateProgress(nextItem + 1)
                handler.postDelayed(this, 5000)
            }
        }

        handler.postDelayed(runnable, 0)
        updateProgress(1)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateProgress(position + 1)
                }
            }
        )
    }

    private fun updateProgress(currentStep: Int) {
        val progressSteps = listOf(
            binding.progressStep1,
            binding.progressStep2,
            binding.progressStep3,
            binding.progressStep4,
            binding.progressStep5
        )

        for (i in progressSteps.indices) {
            if (i < currentStep) {
                progressSteps[i].setBackgroundResource(R.drawable.progress_step_active)
            } else {
                progressSteps[i].setBackgroundResource(R.drawable.progress_step_inactive)
            }
        }
    }


    private fun observeMovies() {
        moviesViewModel.movies.observe(viewLifecycleOwner) { result ->
            when {
                result.isNullOrEmpty() -> {
                    println("No movies found")
                }
                else -> {
                    moviesAdapter.updateMovies(result.take(5))
                    movieList = result.take(5)
                }
            }
        }
    }

    private fun goToMovieScreen(movieId: String) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movieId)
        startActivity(intent)
    }

    private fun onRandomMovieButtonClicked() {
        moviesViewModel.getRandomMovie { randomMovie, error ->
            if (error != null || randomMovie == null) {
                println("Ошибка получения фильма")
            }
            else {
                println("Случайный фильм: ${randomMovie.name}")
                goToMovieScreen(randomMovie.id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}