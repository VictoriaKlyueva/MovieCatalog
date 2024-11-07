package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.common.Constants.LOGIN_ERROR
import com.example.moviecatalog.common.Constants.SUCCESSFUL_LOGIN
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.main.LoginCredentials
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

    fun onSignInButtonClicked(
        user: LoginCredentials,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        loginUseCase.execute(user) { success ->
            if (success) {
                println(SUCCESSFUL_LOGIN)
                onSuccess()
            } else {
                println(LOGIN_ERROR)
                onError()
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