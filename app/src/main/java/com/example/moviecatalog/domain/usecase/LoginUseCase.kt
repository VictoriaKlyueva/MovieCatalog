package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.LoginCredentials
import com.example.moviecatalog.data.repository.AuthRepositoryImpl

class LoginUseCase(
    private val authRepository: AuthRepositoryImpl
) {
    fun execute(user: LoginCredentials, callback: (Boolean) -> Unit) {
        authRepository.loginUser(user, callback)
    }
}