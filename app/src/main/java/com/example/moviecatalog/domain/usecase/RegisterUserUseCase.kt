package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.data.repository.RegisterUserRepository

class RegisterUserUseCase(
    private val registerUserRepository: RegisterUserRepository
) {

    fun execute(user: UserRegisterModel, callback: (Boolean) -> Unit) {
        registerUserRepository.registerUser(user, callback)
    }
}