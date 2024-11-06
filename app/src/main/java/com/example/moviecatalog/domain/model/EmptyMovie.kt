package com.example.moviecatalog.domain.model

import com.example.moviecatalog.data.model.main.MovieElementModel

object EmptyMovie {
    val movie = MovieElementModel (
        id = "1",
        name = null,
        poster = null,
        year = 0,
        country = null,
        genres = emptyList(),
        reviews = emptyList()
    )
}