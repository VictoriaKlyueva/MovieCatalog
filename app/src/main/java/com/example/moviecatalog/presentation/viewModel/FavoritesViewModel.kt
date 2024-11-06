package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.FetchFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.FetchFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetMoviesFromPageUseCase

class FavoritesViewModel : ViewModel() {
    private val movieRepository = MovieRepositoryImpl()
    private val favoritesMoviesRepository = FavoriteMoviesRepositoryImpl()

    private val getMoviesFromPageUseCase = GetMoviesFromPageUseCase(movieRepository)
    private val fetchFavoriteGenresUseCase = FetchFavoriteGenresUseCase(movieRepository)
    private val fetchFavoriteMoviesUseCase = FetchFavoriteMoviesUseCase(favoritesMoviesRepository)

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    private val _favoritesMovies = MutableLiveData<List<MovieElementModel>>()
    val favoritesMovies: LiveData<List<MovieElementModel>> get() = _favoritesMovies

    fun fetchFavoritesGenres(page: Int = (1..5).random()) {
        getMoviesFromPageUseCase.execute(page) { movies, error ->
            if (error == null) {
                if (movies != null) {
                    fetchFavoriteGenresUseCase.execute(movies) { genres ->
                        _favoritesGenres.postValue(genres)
                    }
                } else {
                    _favoritesGenres.postValue(emptyList())
                }
            } else {
                println("Ошибка получения данных: $error")
                _favoritesGenres.postValue(emptyList())
            }
        }
    }

    fun fetchFavoritesMovies() {
        fetchFavoriteMoviesUseCase.execute() { movies, error ->
            if (error == null) {
                if (movies != null) {
                    _favoritesMovies.postValue(movies)
                } else {
                    _favoritesMovies.postValue(emptyList())
                }
            } else {
                println("Ошибка получения данных: $error")
                _favoritesMovies.postValue(emptyList())
            }
        }
    }
}
