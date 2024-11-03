package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.repository.LoginCredentialsRepositoryImpl

class LoginCredentialsUseCase(
    private val loginCredentialsRepository: LoginCredentialsRepositoryImpl
) {
    fun execute(user: LoginCredentials, callback: (Boolean) -> Unit) {
        loginCredentialsRepository.loginUser(user, callback)
    }
}