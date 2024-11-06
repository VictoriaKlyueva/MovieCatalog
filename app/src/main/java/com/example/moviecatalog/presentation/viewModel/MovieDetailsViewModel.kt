package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.EMPTY_STRING
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.KinopoiskRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.AddFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.AddFavoriteUseCase
import com.example.moviecatalog.domain.usecase.CheckFavoriteMovieUseCase
import com.example.moviecatalog.domain.usecase.GetMovieByNameUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.RemoveFavoriteMovieUseCase
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    context: Context
) : ViewModel() {
    private val movieRepository = MovieRepositoryImpl()
    private val favoritesRepository = FavoriteMoviesRepositoryImpl()
    private val kinopoiskRepository = KinopoiskRepositoryImpl()

    private val userDataSource = UserDataSource(context)
    private val movieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private val addFavoriteUseCase = AddFavoriteUseCase(favoritesRepository)
    private val addFavoriteGenresUseCase = AddFavoriteGenresUseCase(userDataSource)
    private val checkFavoriteMovieUseCase = CheckFavoriteMovieUseCase(favoritesRepository)
    private val removeFavoriteMovieUseCase = RemoveFavoriteMovieUseCase(favoritesRepository)
    private val getMovieByNameUseCase = GetMovieByNameUseCase(kinopoiskRepository)

    private val _movie = MutableLiveData<MovieDetailsModel?>()
    val movie: LiveData<MovieDetailsModel?> get() = _movie

    private val _movieEnhanced = MutableLiveData<FilmSearchByFiltersResponse_items?>()
    val movieEnhanced: LiveData<FilmSearchByFiltersResponse_items?> get() = _movieEnhanced

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun toggleFavorite() {
        _isFavorite.value = !(_isFavorite.value ?: false)
    }

    fun addGenreToFavorites(genre: GenreModel) {
        viewModelScope.launch {
            addFavoriteGenresUseCase.execute(genre)
        }
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

    private fun getMovieByName() {
        _movie.value?.let { movieDetails ->
            val movieName = movieDetails.name ?: EMPTY_STRING
            getMovieByNameUseCase.execute(movieName) { movie, error ->

                if (error != null) {
                    println("Ошибка: $error")
                    _errorMessage.postValue(error)
                } else {
                    println("Найденный фильм: $movie")
                    _movieEnhanced.postValue(movie)
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }

    fun fetchMovie(movieId: String) {
        movieDetailsUseCase.execute(movieId) { details, error ->
            if (details != null) {
                _movie.value = details
                _errorMessage.value = null
                getMovieByName()
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

