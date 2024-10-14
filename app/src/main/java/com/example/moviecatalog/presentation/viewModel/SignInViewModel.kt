package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.presentation.utils.ValidationUtils

class SignInViewModel : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    fun onLoginDataChanged(login: String, password: String) {
        _isButtonEnabled.value = login.isNotEmpty() &&
                password.isNotEmpty() &&
                ValidationUtils.isSignInDataValid(login, password)
    }

    fun onBackButtonClicked() {
        _navigateToWelcome.value = true
    }

    fun onNavigated() {
        _navigateToWelcome.value = false
    }
}