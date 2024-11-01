package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.model.ProfileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {
    fun getProfile(callback: (ProfileModel?, String?) -> Unit) {
        val call = ApiClient.apiService.getProfile()
        call.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(
                call: Call<ProfileModel>,
                response: Response<ProfileModel>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(null, errorMessage)
            }
        })
    }
}