package com.example.moviecatalog.data.model.main

data class MovieResponse(
    val movies: List<MovieElementModel>,
    val pageInfoModel: PageInfoModel
)