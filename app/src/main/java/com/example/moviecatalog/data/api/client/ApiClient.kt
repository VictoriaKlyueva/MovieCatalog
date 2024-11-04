package com.example.moviecatalog.data.api.client

import com.example.moviecatalog.common.Constants.BASE_URL
import com.example.moviecatalog.data.api.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val interceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor);

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}