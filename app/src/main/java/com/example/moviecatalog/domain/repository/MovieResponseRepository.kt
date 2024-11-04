package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.MovieElementModel

interface MovieResponseRepository {
    fun getMovies(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit)
}