package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImplImpl

class GetMoviesFromPageUseCase(private val movieResponseRepository: MovieRepositoryImplImpl) {
    fun execute(page: Int, callback: (List<MovieElementModel>?, String?) -> Unit) {
        movieResponseRepository.getMovies(page, callback)
    }
}
