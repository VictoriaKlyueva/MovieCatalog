package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.model.UserRegisterModel

interface AuthRepository {
    fun registerUser(user: UserRegisterModel, callback: (Boolean) -> Unit)

    fun loginUser(user: LoginCredentials, callback: (Boolean) -> Unit)

    fun logout(callback: (Boolean) -> Unit)

    fun isAuthenticated(): Boolean
}