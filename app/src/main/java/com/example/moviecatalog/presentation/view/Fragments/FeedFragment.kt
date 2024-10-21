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
        binding.movieYear.text = movie.year.toString()
        // Здесь вы можете установить картинку постера, если у вас есть URL или ресурс
        // Например:
        // binding.moviePoster.setImageBitmap(loadBitmap(movie.posterUrl))
        // или использовать библиотеку вроде Glide или Picasso
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
