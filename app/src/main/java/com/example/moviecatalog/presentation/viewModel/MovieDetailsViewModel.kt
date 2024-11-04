package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieResponseRepositoryImpl
import com.example.moviecatalog.domain.usecase.MovieResponseUseCase

class MovieDetailsViewModel : ViewModel() {
    private val movieResponseRepository = MovieResponseRepositoryImpl()
    private val movieResponseUseCase = MovieResponseUseCase(movieResponseRepository)

    private val _movies = MutableLiveData<List<MovieElementModel>>()
    val movies: LiveData<List<MovieElementModel>> get() = _movies

    private val _randomMovie = MutableLiveData<MovieElementModel?>()
    val randomMovie: LiveData<MovieElementModel?> get() = _randomMovie

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        val page = (1..5).random()
        movieResponseUseCase.execute(page) { movies, error ->
            if (error == null) {
                _movies.postValue(movies ?: emptyList())
                updateRandomMovie(movies)
            } else {
                println("Ошибка получения данных: $error")
            }
        }
    }

    private fun updateRandomMovie(movieList: List<MovieElementModel>?) {
        if (!movieList.isNullOrEmpty()) {
            val randomIndex = (movieList.indices).random()
            _randomMovie.postValue(movieList[randomIndex])
        } else {
            _randomMovie.postValue(null)
        }
    }
}

