package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.UserShortModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoveFriendUseCase(
    private val userDataSource: UserDataSource
) {

    suspend operator fun invoke(friend: UserShortModel) {
        withContext(Dispatchers.IO) {
            userDataSource.removeFriend(friend)
        }
    }
}