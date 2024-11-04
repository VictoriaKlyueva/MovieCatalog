package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.repository.AuthRepositoryImpl

class LogoutUseCase(
    private val authRepository: AuthRepositoryImpl
) {
    fun execute(callback: (Boolean) -> Unit) {
        authRepository.logout(callback)
    }
}