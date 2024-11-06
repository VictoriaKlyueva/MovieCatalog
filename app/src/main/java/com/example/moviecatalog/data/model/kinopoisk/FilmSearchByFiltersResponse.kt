package com.example.moviecatalog.data.model.kinopoisk

data class FilmSearchByFiltersResponse (
    val total: Int,
    val totalPages: Int,
    val items: List<FilmSearchByFiltersResponse_items>
)