package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.UserShortModel

class AddFriendUseCase(
    private val userDataSource: UserDataSource
) {
    suspend fun execute(friend: UserShortModel) {
        userDataSource.addFriend(friend)
    }
}