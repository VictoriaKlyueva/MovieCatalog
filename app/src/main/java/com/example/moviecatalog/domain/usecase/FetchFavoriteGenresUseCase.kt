package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.GenreModel
import kotlinx.coroutines.flow.first

class FetchFavoriteGenresUseCase(
    private val userDataSource: UserDataSource
) {
    suspend fun execute(): List<GenreModel> {
        return userDataSource.genres.first()
    }
}
