package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.domain.usecase.FetchFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.FetchFriendsUseCase
import com.example.moviecatalog.domain.usecase.RemoveFriendUseCase
import kotlinx.coroutines.launch

class FriendsViewModel(
    context: Context
): ViewModel() {

    private val userDataSource = UserDataSource(context)
    private val fetchFriendsUseCase = FetchFriendsUseCase(userDataSource)
    private val removeFriendUseCase = RemoveFriendUseCase(userDataSource)

    private val _friends = MutableLiveData<List<UserShortModel>>()
    val friends: LiveData<List<UserShortModel>> get() = _friends

    fun fetchFriends() {
        viewModelScope.launch {
            val friends = fetchFriendsUseCase.execute()
            _friends.postValue(friends)
        }
    }

    fun removeFriend(friend: UserShortModel) {
        viewModelScope.launch {
            removeFriendUseCase(friend)
            fetchFriends()
        }
    }
}