package com.example.moviecatalog.data.api.client

import com.example.moviecatalog.common.Constants.BASE_URL_KINOPOISK
import com.example.moviecatalog.data.api.service.ApiKinopoiskService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiKinopoiskClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_KINOPOISK)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiKinopoiskService: ApiKinopoiskService by lazy {
        ApiKinopoiskClient.retrofit.create(ApiKinopoiskService::class.java)
    }
}