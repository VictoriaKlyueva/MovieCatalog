package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.RegisterUserRepositoryImpl

class RegisterUserUseCase(
    private val registerUserRepository: RegisterUserRepositoryImpl
) {

    fun execute(user: UserRegisterModel, callback: (Boolean) -> Unit) {
        registerUserRepository.registerUser(user, callback)
    }
}