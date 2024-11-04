package com.example.moviecatalog.domain.model

import com.example.moviecatalog.data.model.Gender
import com.example.moviecatalog.data.model.ProfileModel

object EmptyProfile {
    val emptyProfile = ProfileModel(
        id = "",
        nickName = "",
        email = "",
        avatarLink = null,
        name = "",
        birthDate = "",
        gender = Gender.fromValue(1)!!
    )
}