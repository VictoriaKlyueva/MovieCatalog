package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieResponseRepository
import com.example.moviecatalog.domain.usecase.MovieResponseUseCase

class MoviesViewModel : ViewModel() {
    private val movieResponseRepository = MovieResponseRepository()
    private val movieResponseUseCase = MovieResponseUseCase(movieResponseRepository)

    private val _movies = MutableLiveData<List<MovieElementModel>>()
    val movies: LiveData<List<MovieElementModel>> get() = _movies

    fun fetchMovies() {
        val page = (1..5).random()
        movieResponseUseCase.execute(page) { movies, error ->
            if (error == null) {
                _movies.postValue(movies?.slice(0 .. 4) ?: emptyList())
            } else {
                println("Ошибка получения данных: $error")
                _movies.postValue(emptyList())
            }
        }
    }

}
