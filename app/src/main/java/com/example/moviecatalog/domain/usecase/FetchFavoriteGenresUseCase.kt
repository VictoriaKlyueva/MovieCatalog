package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl

class FetchFavoriteGenresUseCase(private val movieRepository: MovieRepositoryImpl) {
    fun execute(movies: List<MovieElementModel>, onResult: (List<GenreModel>) -> Unit) {
        val favoriteGenres = mutableListOf<GenreModel>()
        movies.forEach { movie ->
            favoriteGenres.addAll(movie.genres)
        }
        onResult(favoriteGenres.distinct().take(5))
    }
}
