package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserRepository {

    fun registerUser(user: User, callback: (Boolean) -> Unit) {
        ApiClient.apiService.registerUser(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }
}