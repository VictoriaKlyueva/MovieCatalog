package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.repository.LoginRepositoryImpl

class LoginUseCase(
    private val loginCredentialsRepository: LoginRepositoryImpl
) {
    fun execute(user: LoginCredentials, callback: (Boolean) -> Unit) {
        loginCredentialsRepository.loginUser(user, callback)
    }
}