package com.example.moviecatalog.data.model.kinopoisk

data class PersonByNameResponse (
    val total: Int,
    val items: List<PersonByNameResponse_items>
)