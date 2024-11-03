package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.LoginCredentials

interface LoginCredentialsRepository {
    fun loginUser(user: LoginCredentials, callback: (Boolean) -> Unit)
}
