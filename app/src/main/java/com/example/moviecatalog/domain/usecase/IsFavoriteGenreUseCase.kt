package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.GenreModel
import kotlinx.coroutines.flow.first

class IsFavoriteGenreUseCase(
    private val userDataSource: UserDataSource
) {
    suspend fun execute(genre: GenreModel): Boolean {
        val genres =  userDataSource.genres.first()
        return genre.name in genres.map { it.name }
    }
}