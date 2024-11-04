package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieDetailsModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase

class MovieDetailsViewModel : ViewModel() {
    private val movieResponseRepository = MovieRepositoryImpl()
    private val movieDetailsUseCase = GetMovieDetailsUseCase(movieResponseRepository)

    private val _movie = MutableLiveData<MovieDetailsModel?>()
    val movie: LiveData<MovieDetailsModel?> get() = _movie

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchMovie(movieId: String) {
        println("МУВИ АЙДИ")
        println(movieId)

        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: String) {
        movieDetailsUseCase.execute(movieId) { details, error ->
            if (details != null) {
                _movie.value = details
                _errorMessage.value = null
            } else {
                _movie.value = null
                _errorMessage.value = error
            }
        }
    }
}

