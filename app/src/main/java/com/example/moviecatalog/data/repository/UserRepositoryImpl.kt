package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.ApiClient
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.domain.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
): UserRepository {
    override fun getProfile(callback: (ProfileModel?, String?) -> Unit) {
        val call = ApiClient.apiService.getProfile()
        call.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(
                call: Call<ProfileModel>,
                response: Response<ProfileModel>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    val errorMessage = "Ошибка: ${response.code()}"
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                t.printStackTrace()
                val errorMessage = "Ошибка: ${t.message}"
                callback(null, errorMessage)
            }
        })
    }

    override fun isUserExist(userId: String): Boolean {
        return userDataSource.fetchUserId() != null
    }

    override fun getUserIdFromLocalStorage(): String? {
        return userDataSource.fetchUserId()
    }
}