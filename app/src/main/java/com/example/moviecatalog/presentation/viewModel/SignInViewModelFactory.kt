package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.domain.repository.LoginCredentialsRepository

class SignInViewModelFactory(
    private val loginCredentialsRepository: LoginCredentialsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(loginCredentialsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
