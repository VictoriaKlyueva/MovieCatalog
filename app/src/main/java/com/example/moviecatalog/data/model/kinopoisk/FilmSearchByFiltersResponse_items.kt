package com.example.moviecatalog.data.model.kinopoisk

data class FilmSearchByFiltersResponse_items (
    val kinopoiskId: Int,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingKinopoisk: Float?,
    val ratingImdb: Float?,
    val year: Int?,
    val type: String,
    val posterUrl: String,
    val posterUrlPreview: String
)