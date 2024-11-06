package com.example.moviecatalog.presentation.view.FeedScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.databinding.FragmentFeedBinding
import com.example.moviecatalog.presentation.view.MovieDetailsScreen.MovieDetailsActivity
import com.example.moviecatalog.presentation.viewModel.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: FeedViewModel

    private fun hideGenres() {
        binding.genreOne.visibility = View.GONE
        binding.genreTwo.visibility = View.GONE
        binding.genreThree.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)

        hideGenres()

        observeData()
        fetchRandomMovie()

        return binding.root
    }

    private fun fetchRandomMovie() {
        viewModel.getRandomMovie { randomMovie, error ->
            if (error != null) {
                println("Ошибка получения фильма")
            }
        }
    }

    private fun observeData() {
        viewModel.movie.observe(viewLifecycleOwner) { randomMovie ->
            randomMovie?.let {
                displayMovie(it)
            }
        }
    }

    private fun displayMovie(movie: MovieElementModel) {
        binding.movieName.text = movie.name
        binding.movieCountry.text = movie.country
        binding.movieYear.text = movie.year.toString()
        binding.movieSeparator.text = getString(R.string.dot_divider)

        hideGenres()

        movie.genres.take(3).forEachIndexed { index, genre ->
            when (index) {
                0 -> {
                    binding.genreOne.text = genre.name
                    binding.genreOne.visibility = View.VISIBLE
                }
                1 -> {
                    binding.genreTwo.text = genre.name
                    binding.genreTwo.visibility = View.VISIBLE
                }
                2 -> {
                    binding.genreThree.text = genre.name
                    binding.genreThree.visibility = View.VISIBLE
                }
            }
        }

        Glide.with(this)
            .load(movie.poster)
            .transform(RoundedCorners(48))
            .into(binding.moviePoster)

        binding.moviePoster.setOnClickListener {
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}