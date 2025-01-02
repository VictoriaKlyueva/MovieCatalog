package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.common.Constants.MOVIES_NOT_FOUND
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.domain.common.Constants.TOTAL_PAGES
import com.example.moviecatalog.domain.repository.MovieResponseRepository
import kotlin.random.Random

class GetRandomMovieUseCase(private val movieRepository: MovieResponseRepository) {
    fun execute(callback: (MovieElementModel?, String?) -> Unit) {
        val randomPage = Random.nextInt(1, TOTAL_PAGES)

        movieRepository.getMovies(randomPage) { movies, error ->
            if (error != null) {
                callback(null, error)
            } else {
                if (movies.isNullOrEmpty()) {
                    callback(null, MOVIES_NOT_FOUND)
                } else {
                    val randomMovie = movies.random()
                    callback(randomMovie, null)
                }
            }
        }
    }
}
