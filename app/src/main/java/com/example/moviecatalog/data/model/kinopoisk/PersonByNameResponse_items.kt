package com.example.moviecatalog.data.model.kinopoisk

data class PersonByNameResponse_items (
    val kinopoiskId: Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String
)