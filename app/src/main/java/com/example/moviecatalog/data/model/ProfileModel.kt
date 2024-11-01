package com.example.moviecatalog.data.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("nickName")
    val nickName: String? = null,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatarLink")
    val avatarLink: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("gender")
    val gender: Gender
)