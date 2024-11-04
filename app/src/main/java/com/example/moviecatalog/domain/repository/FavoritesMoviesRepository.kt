package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.MovieElementModel

interface FavoritesMoviesRepository {
    fun getFavorites(callback: (List<MovieElementModel>?, String?) -> Unit)
}