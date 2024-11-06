package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.GenreModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AddFavoriteGenresUseCase(
    private val userDataSource: UserDataSource
) {
    suspend fun execute(genre: GenreModel) {
        userDataSource.addGenre(genre)
    }

    fun executeBlocking(genre: GenreModel) {
        runBlocking {
            execute(genre)
        }
    }
}
