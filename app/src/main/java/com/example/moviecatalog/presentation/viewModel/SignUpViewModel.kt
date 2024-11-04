package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.usecase.RegisterUseCase
import com.example.moviecatalog.domain.utils.ValidationUtils

class SignUpViewModel(
    context: Context
) : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    private val tokenDataSource = TokenDataSource(context)
    private val registerRepository = AuthRepositoryImpl(tokenDataSource)
    private val registerUseCase = RegisterUseCase(registerRepository)

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

    fun onSignUpButtonClicked(
        user: UserRegisterModel,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        registerUseCase.execute(user) { success ->
            if (success) {
                println("Успешная регистрация")
                onSuccess()
            } else {
                println("Ошибка(")
                onError()
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


