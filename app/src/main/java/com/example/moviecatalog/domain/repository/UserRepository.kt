package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.ProfileModel

interface UserRepository {
    fun getProfile(callback: (ProfileModel?, String?) -> Unit)

    suspend fun isUserExist(): Boolean

    suspend fun getUserIdFromLocalStorage(): String?
}