package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.FavoritesMoviesRepository

class CheckFavoriteMovieUseCase(
    private val favoritesMoviesRepository: FavoritesMoviesRepository
) {
    fun execute(movieId: String, callback: (Boolean, String?) -> Unit) {
        favoritesMoviesRepository.getFavorites { favoriteMovies, errorMessage ->
            if (errorMessage != null) {
                callback(false, errorMessage)
                return@getFavorites
            }

            val isFavorite = favoriteMovies?.any { it.id == movieId } ?: false
            callback(isFavorite, null)
        }
    }
}
