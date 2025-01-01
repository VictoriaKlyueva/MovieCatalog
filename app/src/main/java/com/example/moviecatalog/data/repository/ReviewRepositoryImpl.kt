package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.domain.repository.ReviewRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewRepositoryImpl : ReviewRepository {
    override fun postReview(
        movieId: String,
        reviewModify: ReviewModifyModel,
        callback: (String?) -> Unit
    ) {
        val call = ApiClient.apiService.postReview(movieId, reviewModify)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(errorMessage)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(errorMessage)
            }
        })
    }

    override fun putReview(
        movieId: String,
        reviewId: String,
        reviewModify: ReviewModifyModel,
        callback: (String?) -> Unit
    ) {
        val call = ApiClient.apiService.putReview(movieId, reviewId, reviewModify)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(errorMessage)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(errorMessage)
            }
        })
    }

    override fun deleteReview(
        movieId: String,
        reviewId: String,
        callback: (String?) -> Unit
    ) {
        val call = ApiClient.apiService.deleteReview(movieId, reviewId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(errorMessage)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(errorMessage)
            }
        })
    }
}