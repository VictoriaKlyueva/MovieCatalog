package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.model.main.MoviesListModel
import com.example.moviecatalog.domain.repository.FavoritesMoviesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteMoviesRepositoryImpl : FavoritesMoviesRepository {

    override fun getFavorites(callback: (List<MovieElementModel>?, String?) -> Unit) {
        val call = ApiClient.apiService.getFavorites()
        call.enqueue(object : Callback<MoviesListModel> {
            override fun onResponse(
                call: Call<MoviesListModel>,
                response: Response<MoviesListModel>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.movies, null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<MoviesListModel>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(null, errorMessage)
            }
        })
    }

    override fun addFavorite(id: String, callback: (String?) -> Unit) {
        val call = ApiClient.apiService.postFavorite(id)
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

    override fun removeFavorite(id: String, callback: (String?) -> Unit) {
        val call = ApiClient.apiService.deleteFavorite(id)
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