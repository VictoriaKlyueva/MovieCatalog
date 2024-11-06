package com.example.moviecatalog.data.api.client

import android.content.Context
import com.example.moviecatalog.common.Constants.BASE_URL
import com.example.moviecatalog.data.api.service.ApiService
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var tokenDataSource: TokenDataSource
    private lateinit var authFailureHandler: AuthInterceptor.AuthFailureHandler

    private val interceptor by lazy {
        AuthInterceptor(tokenDataSource, authFailureHandler)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun init(context: Context, handler: AuthInterceptor.AuthFailureHandler) {
        tokenDataSource = TokenDataSource(context)
        authFailureHandler = handler
    }
}