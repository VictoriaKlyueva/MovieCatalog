package com.example.moviecatalog.data.api.service

import com.example.moviecatalog.common.Constants
import com.example.moviecatalog.data.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiKinopoiskService {
    @Headers(Constants.X_API_KEY)
    @GET("/api/v2.2/films")
    fun getMovies(@Path("page") page: Int): Call<MovieResponse>
}