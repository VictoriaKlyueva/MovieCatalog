package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieResponseRepository
import com.example.moviecatalog.domain.usecase.MovieResponseUseCase

class FeedViewModel : ViewModel() {
    private val movieResponseRepository = MovieResponseRepository()
    private val movieResponseUseCase = MovieResponseUseCase(movieResponseRepository)

    private val _movies = MutableLiveData<List<MovieElementModel>>()
    val movies: LiveData<List<MovieElementModel>> get() = _movies

    fun onDataUploading(page: Int = 1) {
        movieResponseUseCase.execute(page) { movies, error ->
            if (error == null) {
                _movies.postValue(movies!!)
            } else {
                println("Ошибка получения данных: $error")
            }
        }
    }
}

