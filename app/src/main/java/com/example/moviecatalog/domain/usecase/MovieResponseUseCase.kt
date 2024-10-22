package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.repository.MovieResponseRepository

class MovieResponseUseCase(private val movieResponseRepository: MovieResponseRepository) {
    fun execute(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit) {
        movieResponseRepository.getMovies(page, callback)
    }
}
