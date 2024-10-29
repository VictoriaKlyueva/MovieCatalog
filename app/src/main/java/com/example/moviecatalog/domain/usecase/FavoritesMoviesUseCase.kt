package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.FavoritesMoviesRepository

class FavoritesMoviesUseCase(private val favoritesMoviesRepository: FavoritesMoviesRepository) {
    fun execute(callback: (List<MovieElementModel>?, String?) -> Unit) {
        favoritesMoviesRepository.getFavorites(callback)
    }
}