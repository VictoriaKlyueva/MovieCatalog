package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.repository.LoginCredentialsRepository

class LoginCredentialsUseCase(private val userRepository: LoginCredentialsRepository) {

    fun execute(user: LoginCredentials, callback: (Boolean) -> Unit) {
        userRepository.registerUser(user, callback)
    }
}