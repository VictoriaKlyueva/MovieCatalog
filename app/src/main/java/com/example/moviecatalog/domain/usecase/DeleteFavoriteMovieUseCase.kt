package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl

class DeleteFavoriteMovieUseCase(
    private val favoritesMoviesRepository: FavoriteMoviesRepositoryImpl
) {
    fun execute(id: String, callback: (String?) -> Unit) {
        favoritesMoviesRepository.removeFavorite(id, callback)
    }
}