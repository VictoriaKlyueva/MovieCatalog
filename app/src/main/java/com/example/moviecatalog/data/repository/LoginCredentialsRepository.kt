package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.api.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginCredentialsRepository {

    fun registerUser(user: LoginCredentials, callback: (Boolean) -> Unit) {
        NetworkModule.apiService.loginUser(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}