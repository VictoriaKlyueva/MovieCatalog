package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.repository.KinopoiskRepositoryImpl
import com.example.moviecatalog.domain.repository.KinopoiskRepository

class GetMovieByNameUseCase(private val kinopoiskRepository: KinopoiskRepository) {
    fun execute(
        movieName: String,
        callback: (FilmSearchByFiltersResponse_items?, String?) -> Unit
    ) {
        kinopoiskRepository.getMovies { items, error ->
            if (error != null) {
                callback(null, error)
            }

            val movie = items?.find { it.nameRu?.equals(movieName, ignoreCase = true) == true }
            callback(movie, null)
        }
    }
}
