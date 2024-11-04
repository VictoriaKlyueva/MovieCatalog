package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.UserRegisterModel

interface RegisterUserRepository {
    fun registerUser(user: UserRegisterModel, callback: (Boolean) -> Unit)
}