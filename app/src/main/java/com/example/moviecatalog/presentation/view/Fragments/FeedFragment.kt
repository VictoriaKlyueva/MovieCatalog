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
                    displayMovie(it[0])
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
        println(movie.genres.size)
        binding.genreOne.text = movie.genres[0].name
        binding.genreTwo.text = movie.genres[1].name
        binding.genreThree.text = movie.genres[2].name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
