package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.UserShortModel
import kotlinx.coroutines.flow.first

class FetchFriendsUseCase(
    private val userDataSource: UserDataSource
) {
    suspend fun execute(): List<UserShortModel> {
        return userDataSource.friends.first()
    }
}