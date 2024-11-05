package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.FavoritesMoviesRepository

class RemoveFavoriteMovieUseCase(
    private val favoritesMoviesRepository: FavoritesMoviesRepository
) {
    fun execute(movieId: String, callback: (String?) -> Unit) {
        favoritesMoviesRepository.removeFavorite(movieId) { errorMessage ->
            if (errorMessage == null) {
                callback(null)
            } else {
                callback(errorMessage)
            }
        }
    }
}
