package com.example.moviecatalog.data.api.service

import com.example.moviecatalog.common.Constants
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse
import com.example.moviecatalog.data.model.main.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiKinopoiskService {
    @Headers(Constants.X_API_KEY)
    @GET("/api/v2.2/films")
    fun getMovies(): Call<FilmSearchByFiltersResponse>
}