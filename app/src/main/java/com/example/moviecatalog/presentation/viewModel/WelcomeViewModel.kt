package com.example.moviecatalog.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WelcomeViewModel : ViewModel() {

    private val _goToSignIn = MutableLiveData<Boolean>()
    val goToSignIn: LiveData<Boolean> get() = _goToSignIn

    private val _goToSignUp = MutableLiveData<Boolean>()
    val goToSignUp: LiveData<Boolean> get() = _goToSignUp

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
