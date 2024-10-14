package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.presentation.utils.ValidationUtils

class SignUpViewModel : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

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

    fun onBackButtonClicked() {
        _navigateToWelcome.value = true
    }

    fun onNavigationDone() {
        _navigateToWelcome.value = false
    }
}
