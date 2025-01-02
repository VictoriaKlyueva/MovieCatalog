package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.data.repository.UserRepositoryImpl

class EditProfileUseCase(private val userRepository: UserRepositoryImpl) {
    fun execute(
        profile: ProfileModel,
        callback: (String?) -> Unit
    ) {
        userRepository.editProfile(profile, callback)
    }
}