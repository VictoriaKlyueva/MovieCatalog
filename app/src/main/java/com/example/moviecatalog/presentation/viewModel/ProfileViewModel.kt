package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.UserRepositoryImpl
import com.example.moviecatalog.domain.model.EmptyProfile
import com.example.moviecatalog.domain.usecase.LogoutUseCase
import com.example.moviecatalog.domain.usecase.GetProfileUseCase

class ProfileViewModel(
    context: Context
): ViewModel() {

    private val tokenDataSource = TokenDataSource(context)
    private val userDataSource = UserDataSource(context)

    private val authRepository = AuthRepositoryImpl(tokenDataSource)
    private val userRepository = UserRepositoryImpl(userDataSource)

    private val logoutUseCase = LogoutUseCase(authRepository)
    private val userUseCase = GetProfileUseCase(userRepository)

    private val _profile = MutableLiveData<ProfileModel>()
    val profile: LiveData<ProfileModel> get() = _profile

    fun onLogoutButtonClicked(callback: (Boolean) -> Unit) {
        logoutUseCase.execute { success ->
            if (success) {
                println("Успешный выход из аккаунта")
            } else {
                println("Ошибка выхода")
            }
            callback(success)
        }
    }

    fun getProfileData(callback: (ProfileModel?, String?) -> Unit) {
        userUseCase.execute { receivedProfile, error ->
            if (receivedProfile != null) {
                _profile.postValue(receivedProfile ?: EmptyProfile.emptyProfile)
                callback(receivedProfile, null)
            } else {
                callback(null, error)
            }
        }
    }
}
