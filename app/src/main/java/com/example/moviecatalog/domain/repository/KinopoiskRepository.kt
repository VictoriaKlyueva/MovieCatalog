package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items

interface KinopoiskRepository {
    fun getMovies(callback: (List<FilmSearchByFiltersResponse_items>?, String?) -> Unit)
}