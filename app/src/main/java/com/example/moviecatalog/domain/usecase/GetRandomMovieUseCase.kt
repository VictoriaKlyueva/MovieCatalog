package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.domain.conmmon.Constants.TOTAL_PAGES
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
                    callback(null, "Фильмы не найдены")
                } else {
                    val randomMovie = movies.random()
                    callback(randomMovie, null)
                }
            }
        }
    }
}
