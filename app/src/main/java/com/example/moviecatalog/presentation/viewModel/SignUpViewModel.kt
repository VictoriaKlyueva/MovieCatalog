package com.example.moviecatalog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val _navigateToWelcome = MutableLiveData<Boolean>()
    val navigateToWelcome: LiveData<Boolean> get() = _navigateToWelcome

    fun onBackButtonClicked() {
        _navigateToWelcome.value = true
    }

    fun onNavigationDone() {
        _navigateToWelcome.value = false
    }
}
