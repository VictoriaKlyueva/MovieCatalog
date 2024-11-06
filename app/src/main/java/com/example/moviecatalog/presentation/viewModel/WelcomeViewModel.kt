package com.example.moviecatalog.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.usecase.IsAuthenticatedUseCase

class WelcomeViewModel(
    context: Context
) : ViewModel() {
    private val _goToSignIn = MutableLiveData<Boolean>()
    val goToSignIn: LiveData<Boolean> get() = _goToSignIn

    private val _goToSignUp = MutableLiveData<Boolean>()
    val goToSignUp: LiveData<Boolean> get() = _goToSignUp

    private val tokenDataSource = TokenDataSource(context)
    private val authRepositoryImpl = AuthRepositoryImpl(tokenDataSource)
    private val isAuthenticatedUseCase = IsAuthenticatedUseCase(authRepositoryImpl)

    fun isAuthorized(): Boolean {
        return isAuthenticatedUseCase.execute()
    }

    fun onLoginButtonClicked() {
        _goToSignIn.value = true
    }

    fun onRegisterButtonClicked() {
        _goToSignUp.value = true
    }

    fun resetSignInNavigation() {
        _goToSignIn.value = false
    }

    fun resetSignUpNavigation() {
        _goToSignUp.value = false
    }
}
