package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.MovieResponseUseCase

class FavoritesViewModel : ViewModel() {
    private val movieResponseRepository = MovieRepositoryImpl()
    private val movieResponseUseCase = MovieResponseUseCase(movieResponseRepository)

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    private val _favoritesMovies = MutableLiveData<List<MovieElementModel>>()
    val favoritesMovies: LiveData<List<MovieElementModel>> get() = _favoritesMovies

    fun fetchFavoritesGenres(page: Int = (1..5).random()) {
        movieResponseUseCase.execute(page) { movies, error ->
            if (error == null) {
                val favoriteGenres = mutableListOf<GenreModel>()
                movies?.forEach { movie ->
                    favoriteGenres.addAll(movie.genres)
                }
                _favoritesGenres.postValue(favoriteGenres.distinct().take(5))
            } else {
                println("Ошибка получения данных: $error")
                _favoritesGenres.postValue(emptyList())
            }
        }
    }

    fun fetchFavoritesMovies() {
        for (page in (1..5)) {
            movieResponseUseCase.execute(page) { movies, error ->
                if (error == null) {
                    _favoritesMovies.postValue(movies)
                } else {
                    println("Ошибка получения данных: $error")
                    _favoritesMovies.postValue(emptyList())
                }
            }
        }
    }
}