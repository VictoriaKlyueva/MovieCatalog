package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.main.MovieElementModel

interface FavoritesMoviesRepository {
    fun getFavorites(callback: (List<MovieElementModel>?, String?) -> Unit)

    fun addFavorite(id: String, callback: (String?) -> Unit)

    fun removeFavorite(id: String, callback: (String?) -> Unit)
}