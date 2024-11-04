package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.usecase.LoginUseCase
import com.example.moviecatalog.domain.utils.ValidationUtils

class SignInViewModel(
    context: Context
) : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    private val tokenDataSource = TokenDataSource(context)
    private val authRepository = AuthRepositoryImpl(tokenDataSource)
    private val loginUseCase = LoginUseCase(authRepository)

    fun onSignInDataChanged(login: String, password: String) {
        _isButtonEnabled.value = ValidationUtils.isSignInDataValid(login, password)
    }

    fun onSignInButtonClicked(user: LoginCredentials) {
        loginUseCase.execute(user) { success ->
            if (success) {
                println("Успешный вход в аккаунт")
            } else {
                println("Ошибка входа(")
            }
        }
    }

    fun onBackButtonClicked() {
        _navigateToWelcome.value = true
    }

    fun onNavigated() {
        _navigateToWelcome.value = false
    }
}