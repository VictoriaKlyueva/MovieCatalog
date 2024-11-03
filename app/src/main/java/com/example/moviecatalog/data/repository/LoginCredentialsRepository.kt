package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.Token
import com.example.moviecatalog.domain.repository.LoginCredentialsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginCredentialsRepositoryImpl(
    private val tokenDataSource: TokenDataSource
) : LoginCredentialsRepository {
    override fun loginUser(user: LoginCredentials, callback: (Boolean) -> Unit) {
        ApiClient.apiService.loginUser(user).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    response.body()?.let { token ->
                        tokenDataSource.save(token.token)
                        callback(true)
                    } ?: callback(false)
                } else {
                    println("Login Error: ${response.code()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                println("Login Failure: ${t.message}")
                callback(false)
            }
        })
    }
}
