package com.example.moviecatalog.data.api.service

import com.example.moviecatalog.common.Constants
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse
import com.example.moviecatalog.data.model.kinopoisk.PersonByNameResponse
import com.example.moviecatalog.data.model.main.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiKinopoiskService {
    @Headers("X-API-KEY: ${Constants.X_API_KEY}")
    @GET("/api/v2.2/films")
    fun getMovies(@Query("page") page: Int): Call<FilmSearchByFiltersResponse>

    @Headers("X-API-KEY: ${Constants.X_API_KEY}")
    @GET("/api/v1/persons")
    fun getDirector(@Query("name") name: String, @Query("page") page: Int): Call<PersonByNameResponse>
}