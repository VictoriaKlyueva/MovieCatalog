package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.UserRepositoryImpl

class ProfileUseCase(private val profileRepository: UserRepositoryImpl) {
    fun execute(callback: (ProfileModel?, String?) -> Unit) {
        profileRepository.getProfile(callback)
    }
}