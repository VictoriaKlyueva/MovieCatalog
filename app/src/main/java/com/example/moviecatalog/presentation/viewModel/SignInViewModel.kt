package com.example.moviecatalog.presentation.viewModel

import android.widget.Toast
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

    private val userRepository = LoginCredentialsRepository()
    private val registerUserUseCase = LoginCredentialsUseCase(userRepository)

    fun onSignInDataChanged(login: String, password: String) {
        _isButtonEnabled.value = ValidationUtils.isSignInDataValid(login, password)
    }

    fun onSignInButtonClicked(user: LoginCredentials) {
        registerUserUseCase.execute(user) { success ->
            if (success) {
                println("Успешная регистрация")
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