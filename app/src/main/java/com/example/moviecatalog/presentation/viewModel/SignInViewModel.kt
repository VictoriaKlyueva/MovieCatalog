package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.repository.LoginCredentialsRepository
import com.example.moviecatalog.domain.usecase.LoginCredentialsUseCase
import com.example.moviecatalog.presentation.utils.ValidationUtils

class SignInViewModel : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    private val loginRepository = LoginCredentialsRepository()
    private val loginCredentialsUseCase = LoginCredentialsUseCase(loginRepository)

    fun onSignInDataChanged(login: String, password: String) {
        _isButtonEnabled.value = ValidationUtils.isSignInDataValid(login, password)
    }

    fun onSignInButtonClicked(user: LoginCredentials) {
        loginCredentialsUseCase.execute(user) { success ->
            if (success) {
                println("Успешный вход в аккаунт")
            } else {
                println("Ошибка(")
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