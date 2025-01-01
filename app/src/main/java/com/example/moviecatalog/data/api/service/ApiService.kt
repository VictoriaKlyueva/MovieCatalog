package com.example.moviecatalog.data.api.service

import com.example.moviecatalog.data.model.main.LoginCredentials
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.model.main.MovieResponse
import com.example.moviecatalog.data.model.main.MoviesListModel
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.domain.token.Token
import com.example.moviecatalog.data.model.main.UserRegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/api/account/login")
    fun loginUser(@Body user: LoginCredentials): Call<Token>

    @POST("/api/account/register")
    fun registerUser(@Body user: UserRegisterModel): Call<Token>

    @POST("api/account/logout")
    fun logout(): Call<Void>

    @GET("api/favorites")
    fun getFavorites(): Call<MoviesListModel>

    @POST("api/favorites/{id}/add")
    fun postFavorite(@Path("id") id: String): Call<Void>

    @DELETE("api/favorites/{id}/delete")
    fun deleteFavorite(@Path("id") id: String): Call<Void>

    @GET("api/movies/{page}")
    fun getMovies(@Path("page") page: Int): Call<MovieResponse>

    @GET("api/movies/details/{id}")
    fun getMoviesDetails(@Path("id") id: String): Call<MovieDetailsModel>

    @POST("/api/movie/{movieId}/review/add")
    fun postReview(
        @Path("movieId") movieId: String,
        @Body reviewModify: ReviewModifyModel
    ): Call<Void>

    @PUT("/api/movie/{movieId}/review/{id}/edit")
    fun putReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Body reviewModify: ReviewModifyModel
    ): Call<Void>

    @DELETE("/api/movie/{movieId}/review/{id}/edit")
    fun deleteReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
    ): Call<Void>

    @GET("/api/account/profile")
    fun getProfile(): Call<ProfileModel>
}