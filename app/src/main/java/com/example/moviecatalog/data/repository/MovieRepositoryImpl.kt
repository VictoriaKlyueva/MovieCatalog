package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.model.main.MovieResponse
import com.example.moviecatalog.domain.repository.MovieResponseRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl : MovieResponseRepository {

    override fun getMovies(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit) {
        val call = ApiClient.apiService.getMovies(page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
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

    fun getMovieDetails(id: String, callback: (MovieDetailsModel?, String?) -> Unit) {
        val call = ApiClient.apiService.getMoviesDetails(id)
        call.enqueue(object : Callback<MovieDetailsModel> {
            override fun onResponse(
                call: Call<MovieDetailsModel>,
                response: Response<MovieDetailsModel>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    println(errorMessage)
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                println(errorMessage)
                callback(null, errorMessage)
            }
        })
    }
}

