package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.domain.repository.MovieResponseRepositoryImpl

class GetAllMoviesUseCase(private val movieRepository: MovieResponseRepositoryImpl) {

    fun execute(callback: (List<MovieElementModel>?, String?) -> Unit) {
        val allMovies = mutableListOf<MovieElementModel>()
        val totalPages = 1

        fun fetchMovies(page: Int) {
            movieRepository.getMovies(page) { movies, error ->
                if (error != null) {
                    callback(null, error)
                }

                if (movies != null) {
                    allMovies.addAll(movies)

                    if (movies.size < PAGE_SIZE) {
                        callback(allMovies, null)
                    } else {
                        fetchMovies(page + 1)
                    }
                } else {
                    callback(allMovies, null)
                }
            }
        }

        fetchMovies(totalPages)
    }

    companion object {
        private const val PAGE_SIZE = 6
    }
}
