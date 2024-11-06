package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {
    override fun getProfile(callback: (ProfileModel?, String?) -> Unit) {
        val call = ApiClient.apiService.getProfile()
        call.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(
                call: Call<ProfileModel>,
                response: Response<ProfileModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { profile ->
                        callback(profile, null)
                    } ?: callback(null, "Profile is null")
                } else {
                    val errorMessage = "Error: ${response.code()}"
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                val errorMessage = "Error: ${t.message ?: "Unknown error"}"
                callback(null, errorMessage)
            }
        })
    }

    override suspend fun isUserExist(): Boolean {
        return userDataSource.userId.first() != null
    }

    override suspend fun getUserIdFromLocalStorage(): String? {
        return userDataSource.userId.firstOrNull()
    }
}