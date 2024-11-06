package com.example.moviecatalog.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NavigationManager {
    private val _navigateToWelcomeScreen = MutableLiveData<Boolean>()
    val navigateToWelcomeScreen: LiveData<Boolean> get() = _navigateToWelcomeScreen

    fun notifyUserToGoToWelcomeScreen() {
        _navigateToWelcomeScreen.postValue(true)
    }
}
