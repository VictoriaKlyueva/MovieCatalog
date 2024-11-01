package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.ProfileRepository

class ProfileUseCase(private val profileRepository: ProfileRepository) {
    fun execute(callback: (ProfileModel?, String?) -> Unit) {
        profileRepository.getProfile(callback)
    }
}