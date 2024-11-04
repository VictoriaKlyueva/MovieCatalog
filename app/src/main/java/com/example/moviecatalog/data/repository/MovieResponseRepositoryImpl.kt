package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.model.MovieResponse
import com.example.moviecatalog.domain.repository.MovieResponseRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieResponseRepositoryImpl: MovieResponseRepository {
    override fun getMovies(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit) {
        val call = ApiClient.apiService.getMovies(page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.movies, null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    println(errorMessage)
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                println(errorMessage)
                callback(null, errorMessage)
            }
        })
    }
}
