package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.User
import com.example.moviecatalog.data.repository.RegisterUserRepository

class RegisterUserUseCase(private val registerUserRepository: RegisterUserRepository) {

    fun execute(user: User, callback: (Boolean) -> Unit) {
        registerUserRepository.registerUser(user, callback)
    }
}