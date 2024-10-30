package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.data.repository.MovieResponseRepository
import com.example.moviecatalog.domain.usecase.MovieResponseUseCase

class FavoritesViewModel : ViewModel() {
    private val movieResponseRepository = MovieResponseRepository()
    private val movieResponseUseCase = MovieResponseUseCase(movieResponseRepository)

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    fun fetchFavoriteGenres(page: Int = (1..5).random()) {
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
}