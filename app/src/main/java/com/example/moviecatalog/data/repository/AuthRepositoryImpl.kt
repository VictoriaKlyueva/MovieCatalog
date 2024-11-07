package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.main.LoginCredentials
import com.example.moviecatalog.domain.token.Token
import com.example.moviecatalog.data.model.main.UserRegisterModel
import com.example.moviecatalog.domain.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepositoryImpl(
    private val tokenDataSource: TokenDataSource
): AuthRepository {

    override fun registerUser(user: UserRegisterModel, callback: (Boolean) -> Unit) {
        ApiClient.apiService.registerUser(user).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    response.body()?.let { token ->
                        tokenDataSource.save(token.token)
                        callback(true)
                    } ?: callback(false)
                } else {
                    println("Register Error: ${response.code()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                println("Register Failure: ${t.message}")
                callback(false)
            }
        })
    }

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

    override fun logout(callback: (Boolean) -> Unit) {
        ApiClient.apiService.logout().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    tokenDataSource.delete()
                    callback(true)
                } else {
                    println("Logout Error: ${response.code()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Logout Failure: ${t.message}")
                callback(false)
            }
        })
    }

    override fun isAuthenticated(): Boolean {
        return tokenDataSource.getToken() != null
    }
}
