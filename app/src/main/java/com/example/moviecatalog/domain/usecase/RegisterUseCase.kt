package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.AuthRepositoryImpl

class RegisterUseCase(
    private val registerUserRepository: AuthRepositoryImpl
) {
    fun execute(user: UserRegisterModel, callback: (Boolean) -> Unit) {
        registerUserRepository.registerUser(user, callback)
    }
}