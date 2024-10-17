package com.example.moviecatalog.data.api

import com.example.moviecatalog.data.model.LoginCredentials
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/account/login")
    fun loginUser(@Body user: LoginCredentials): Call<Void>
}