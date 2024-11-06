package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.datasource.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoveGenreUseCase(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(genre: GenreModel) {
        withContext(Dispatchers.IO) {
            userDataSource.removeGenre(genre)
        }
    }
}