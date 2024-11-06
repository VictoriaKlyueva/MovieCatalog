package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiKinopoiskClient.apiKinopoiskService
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.domain.repository.KinopoiskRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KinopoiskRepositoryImpl : KinopoiskRepository {
    override fun getMovies(callback: (List<FilmSearchByFiltersResponse_items>?, String?) -> Unit) {
        val allMovies = mutableListOf<FilmSearchByFiltersResponse_items>()
        var currentPage = 1

        fun fetchPage(page: Int) {
            apiKinopoiskService.getMovies(page).enqueue(
                object : Callback<FilmSearchByFiltersResponse> {
                override fun onResponse(
                    call: Call<FilmSearchByFiltersResponse>,
                    response: Response<FilmSearchByFiltersResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        allMovies.addAll(responseBody.items)

                        if (currentPage < responseBody.totalPages) {
                            currentPage++
                            fetchPage(currentPage)
                        } else {
                            callback(allMovies, null)
                        }
                    } else {
                        callback(null, "Error retrieving movies: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<FilmSearchByFiltersResponse>, t: Throwable) {
                    callback(null, t.message)
                }
            })
        }

        fetchPage(currentPage)
    }
}
