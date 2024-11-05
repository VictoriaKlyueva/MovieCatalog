package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.domain.repository.FavoritesMoviesRepository

class FetchFavoriteMoviesUseCase(
    private val favoritesMoviesRepository: FavoritesMoviesRepository
) {
    fun execute(callback: (List<MovieElementModel>?, String?) -> Unit) {
        favoritesMoviesRepository.getFavorites { movies, error ->
            if (error != null) {
                callback(null, error)
            } else {
                callback(movies, null)
            }
        }
    }
}
