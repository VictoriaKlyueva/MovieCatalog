package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.RegisterRepositoryImpl

class RegisterUseCase(
    private val registerUserRepository: RegisterRepositoryImpl
) {
    fun execute(user: UserRegisterModel, callback: (Boolean) -> Unit) {
        registerUserRepository.registerUser(user, callback)
    }
}