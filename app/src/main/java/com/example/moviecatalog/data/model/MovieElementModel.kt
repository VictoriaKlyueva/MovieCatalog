package com.example.moviecatalog.data.model

import java.io.Serializable

data class MovieElementModel (
    val id: String,
    val name: String? = null,
    val poster: String? = null,
    val year: Int,
    val country: String? = null,
    val genres: List<GenreModel>,
    val reviews: List<ReviewShortModel>
) : Serializable