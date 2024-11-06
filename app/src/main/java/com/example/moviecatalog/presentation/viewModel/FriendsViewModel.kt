package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.main.ProfileModel

class FriendsViewModel: ViewModel() {
    //private val profileRepository = UserRepository()
    //private val profileUseCase = ProfileUseCase(profileRepository)

    private val _friends = MutableLiveData<List<ProfileModel>>()
    val friends: LiveData<List<ProfileModel>> get() = _friends
}