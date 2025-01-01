package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.main.MovieElementModel

interface MovieResponseRepositoryImpl {
    fun getMovies(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit)
}