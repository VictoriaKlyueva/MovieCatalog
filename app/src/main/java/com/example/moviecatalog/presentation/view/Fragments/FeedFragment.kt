package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.FragmentFeedBinding
import com.example.moviecatalog.presentation.viewModel.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                if (it.isNotEmpty()) {
                    displayMovie(viewModel.getRandomMovie(it))
                }
            }
        }

        viewModel.onDataUploading()

        return binding.root
    }


    private fun displayMovie(movie: MovieElementModel) {
        binding.movieName.text = movie.name
        binding.movieCountry.text = movie.country
        binding.movieSeparator.text = " • "
        binding.movieYear.text = movie.year.toString()

        binding.genreOne.visibility = View.GONE
        binding.genreTwo.visibility = View.GONE
        binding.genreThree.visibility = View.GONE

        for (i in movie.genres.indices) {
            when (i) {
                0 -> {
                    binding.genreOne.text = movie.genres[i].name
                    binding.genreOne.visibility = View.VISIBLE
                }
                1 -> {
                    binding.genreTwo.text = movie.genres[i].name
                    binding.genreTwo.visibility = View.VISIBLE
                }
                2 -> {
                    binding.genreThree.text = movie.genres[i].name
                    binding.genreThree.visibility = View.VISIBLE
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
