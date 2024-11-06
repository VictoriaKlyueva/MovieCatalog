package com.example.moviecatalog.domain.model

import com.example.moviecatalog.data.model.main.ProfileModel

object EmptyProfile {
    val emptyProfile = ProfileModel(
        id = "",
        nickName = "",
        email = "",
        avatarLink = null,
        name = "",
        birthDate = "",
        gender = 1
    )
}