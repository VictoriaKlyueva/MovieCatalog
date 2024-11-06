package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.main.LoginCredentials

interface LoginCredentialsRepository {
    fun loginUser(user: LoginCredentials, callback: (Boolean) -> Unit)
}
