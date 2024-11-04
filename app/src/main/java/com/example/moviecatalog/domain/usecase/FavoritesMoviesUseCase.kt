package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl

class FavoritesMoviesUseCase(private val favoritesMoviesRepository: FavoriteMoviesRepositoryImpl) {
    fun execute(callback: (List<MovieElementModel>?, String?) -> Unit) {
        favoritesMoviesRepository.getFavorites(callback)
    }
}