package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.repository.AuthRepositoryImpl

class IsAuthenticatedUseCase(
    private val authRepository: AuthRepositoryImpl
) {
    fun execute() : Boolean {
        return authRepository.isAuthenticated()
    }
}