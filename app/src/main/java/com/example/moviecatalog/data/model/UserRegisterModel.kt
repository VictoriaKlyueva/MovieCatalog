package com.example.moviecatalog.data.model


import com.google.gson.annotations.SerializedName

data class UserRegisterModel(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    @SerializedName("birthDate") val birthDate: String,
    @SerializedName("Gender") val gender: Gender
)
