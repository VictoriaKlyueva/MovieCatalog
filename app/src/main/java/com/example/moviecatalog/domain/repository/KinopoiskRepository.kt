package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.model.kinopoisk.PersonByNameResponse_items

interface KinopoiskRepository {
    fun getMovies(callback: (List<FilmSearchByFiltersResponse_items>?, String?) -> Unit)

    fun getDirector(name: String, page: Int, callback: (List<PersonByNameResponse_items>?, String?) -> Unit)
}