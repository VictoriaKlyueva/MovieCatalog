package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.ProfileModel

interface UserRepository {
    fun getProfile(callback: (ProfileModel?, String?) -> Unit)

    fun isUserExist(userId: String): Boolean

    fun getUserIdFromLocalStorage(): String?
}