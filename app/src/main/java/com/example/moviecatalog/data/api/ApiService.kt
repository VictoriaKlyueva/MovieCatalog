package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.model.MovieResponse
import com.example.moviecatalog.data.model.MoviesListModel
import com.example.moviecatalog.data.model.UserRegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/account/login")
    fun loginUser(@Body user: LoginCredentials): Call<Void>

    @POST("/api/account/register")
    fun registerUser(@Body user: UserRegisterModel): Call<Void>

    @GET("api/movies/{page}")
    fun getMovies(@Path("page") page: Int): Call<MovieResponse>

    @GET("api/favorites")
    fun getFavorites(): Call<MoviesListModel>
}