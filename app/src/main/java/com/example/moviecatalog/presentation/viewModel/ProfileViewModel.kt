package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.LOGOUT_ERROR
import com.example.moviecatalog.common.Constants.SUCCESSFUL_LOGOUT
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserRepositoryImpl
import com.example.moviecatalog.domain.usecase.EditProfileUseCase
import com.example.moviecatalog.domain.usecase.FetchFriendsUseCase
import com.example.moviecatalog.domain.usecase.LogoutUseCase
import com.example.moviecatalog.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    context: Context
): ViewModel() {
    private val tokenDataSource = TokenDataSource(context)
    private val userDataSource = UserDataSource(context)

    private val authRepository = AuthRepositoryImpl(tokenDataSource)
    private val userRepository = UserRepositoryImpl(userDataSource)

    private val logoutUseCase = LogoutUseCase(authRepository)
    private val getProfileUseCase = GetProfileUseCase(userRepository)
    private val editProfileUseCase = EditProfileUseCase(userRepository)
    private val fetchFriendsUseCase = FetchFriendsUseCase(userDataSource)

    private val _profile = MutableLiveData<ProfileModel?>()
    val profile: MutableLiveData<ProfileModel?> get() = _profile

    private val _friends = MutableLiveData<List<UserShortModel>>()
    val friends: LiveData<List<UserShortModel>> get() = _friends

    fun onLogoutButtonClicked(callback: (Boolean) -> Unit) {
        logoutUseCase.execute { success ->
            if (success) {
                println(SUCCESSFUL_LOGOUT)
            } else {
                println(LOGOUT_ERROR)
            }
            callback(success)
        }
    }

    fun getProfileData(callback: (ProfileModel?, String?) -> Unit) {
        getProfileUseCase.execute { receivedProfile, error ->
            if (receivedProfile != null) {
                _profile.postValue(receivedProfile)
                callback(receivedProfile, null)
            } else {
                callback(null, error)
            }
        }
    }

    fun editProfileData(profile: ProfileModel) {
         editProfileUseCase.execute(profile) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Профиль успешно отредактирован")

                    getProfileData { updatedProfile, _ ->
                        if (updatedProfile != null) {
                            _profile.postValue(updatedProfile)
                        }
                    }
                }
            }
    }

    fun fetchFriends() {
        viewModelScope.launch {
            val friends = fetchFriendsUseCase.execute()
            _friends.postValue(friends)
        }
    }
}
