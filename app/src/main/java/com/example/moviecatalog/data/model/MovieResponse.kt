package com.example.moviecatalog.data.model

data class MovieResponse(
    val movies: List<MovieElementModel>,
    val pageInfo: PageInfo
)