package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.UserRepository

class ProfileUseCase(private val profileRepository: UserRepository) {
    fun execute(callback: (ProfileModel?, String?) -> Unit) {
        profileRepository.getProfile(callback)
    }
}