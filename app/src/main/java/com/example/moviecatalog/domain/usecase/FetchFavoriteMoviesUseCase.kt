package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl

class FetchFavoriteMoviesUseCase(private val movieRepository: MovieRepositoryImpl) {
    fun execute(pages: IntRange, onResult: (List<MovieElementModel>?, String?) -> Unit) {
        val accumulatedMovies = mutableListOf<MovieElementModel>()
        var errorOccurred: String? = null

        pages.forEach { page ->
            movieRepository.getMovies(page) { movies, error ->
                if (error != null) {
                    errorOccurred = error
                }

                if (movies != null) {
                    accumulatedMovies.addAll(movies)
                }

                onResult(accumulatedMovies, errorOccurred)
            }
        }
    }
}
