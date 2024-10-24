package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.presentation.view.Adapters.MoviesAdapter
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MoviesViewModel by viewModels()
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
        movieViewModel.fetchMovies()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter(emptyList())
        binding.viewPager.adapter = moviesAdapter

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPager.currentItem
                val nextItem = if (currentItem == moviesAdapter.itemCount - 1)
                    0
                else
                    currentItem + 1

                binding.viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 5000)
            }
        }

        handler.postDelayed(runnable, 3000)
    }


    private fun observeMovies() {
        binding.progressBar.visibility = View.VISIBLE

        movieViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            binding.progressBar.visibility = View.GONE
            moviesAdapter.updateMovies(movies)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
