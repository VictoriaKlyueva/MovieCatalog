package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieDetailsModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.AddFavoriteUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase

class MovieDetailsViewModel : ViewModel() {
    private val movieRepository = MovieRepositoryImpl()
    private val favoritesRepository = FavoriteMoviesRepositoryImpl()

    private val movieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private val addFavoriteUseCase = AddFavoriteUseCase(favoritesRepository)

    private val _movie = MutableLiveData<MovieDetailsModel?>()
    val movie: LiveData<MovieDetailsModel?> get() = _movie

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchMovie(movieId: String) {
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

    fun addToFavorite() {
        _movie.value?.let { movieDetails ->
            val movieId = movieDetails.id
            addFavoriteUseCase.execute(movieId) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Успешно добавлено в избранное")
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }
}

