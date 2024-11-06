package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.AddFavoriteUseCase
import com.example.moviecatalog.domain.usecase.CheckFavoriteMovieUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.RemoveFavoriteMovieUseCase

class MovieDetailsViewModel : ViewModel() {
    private val movieRepository = MovieRepositoryImpl()
    private val favoritesRepository = FavoriteMoviesRepositoryImpl()

    private val movieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private val addFavoriteUseCase = AddFavoriteUseCase(favoritesRepository)
    private val checkFavoriteMovieUseCase = CheckFavoriteMovieUseCase(favoritesRepository)
    private val removeFavoriteMovieUseCase = RemoveFavoriteMovieUseCase(favoritesRepository)

    private val _movie = MutableLiveData<MovieDetailsModel?>()
    val movie: LiveData<MovieDetailsModel?> get() = _movie

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun toggleFavorite() {
        _isFavorite.value = !(_isFavorite.value ?: false)
    }

    private fun checkFavorite(movieId: String) {
        checkFavoriteMovieUseCase.execute(movieId) { isFavoriteMovie, error ->
            if (error != null) {
                _errorMessage.postValue(error)
                return@execute
            }
            _isFavorite.postValue(isFavoriteMovie)
        }
    }

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

        checkFavorite(movieId)
    }

    fun removeFavorite() {
        _movie.value?.let { movieDetails ->
            val movieId = movieDetails.id
            removeFavoriteMovieUseCase.execute(movieId) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Успешно удалено из избранного")
                }
            }
        } ?: run {
            println("Фильм не доступен")
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

