package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.UserRepositoryImpl

class GetProfileUseCase(private val userRepository: UserRepositoryImpl) {
    fun execute(callback: (ProfileModel?, String?) -> Unit) {
        userRepository.getProfile(callback)
    }
}