package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.repository.MovieRepositoryImplImpl

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepositoryImplImpl
) {
    fun execute(movieId: String, callback: (MovieDetailsModel?, String?) -> Unit) {
        movieRepository.getMovieDetails(movieId, callback)
    }
}