package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.model.Token
import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.domain.repository.RegisterUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserRepositoryImpl(
    private val tokenDataSource: TokenDataSource
): RegisterUserRepository {
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
}
