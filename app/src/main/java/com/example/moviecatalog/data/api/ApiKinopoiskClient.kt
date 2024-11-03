package com.example.moviecatalog.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiKinopoiskClient {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/documentation/api/#/films/get_api_v2_2_films"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}