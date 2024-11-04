package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl

class MovieResponseUseCase(private val movieResponseRepository: MovieRepositoryImpl) {
    fun execute(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit) {
        movieResponseRepository.getMovies(page, callback)
    }
}
