package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieDetailsModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepositoryImpl
) {
    fun execute(movieId: String, callback: (MovieDetailsModel?, String?) -> Unit) {
        movieRepository.getMovieDetails(movieId, callback)
    }
}