package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.RegisterUserRepository
import com.example.moviecatalog.domain.usecase.RegisterUserUseCase
import com.example.moviecatalog.presentation.utils.ValidationUtils

class SignUpViewModel : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    private val userRepository = RegisterUserRepository()
    private val registerUserUseCase = RegisterUserUseCase(userRepository)

    fun onSignUpDataChanged(
        login: String,
        email: String,
        name: String,
        password: String,
        confirmPassword: String,
        dateOfBirth: String,
        isMale: Boolean,
        isFemale: Boolean
    ) {
        _isButtonEnabled.value = ValidationUtils.isSignUpDataValid(
            login,
            email,
            name,
            password,
            confirmPassword,
            dateOfBirth,
            isMale,
            isFemale
        )
    }

    fun onSignUpButtonClicked(user: UserRegisterModel) {
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

    fun onNavigationDone() {
        _navigateToWelcome.value = false
    }
}


