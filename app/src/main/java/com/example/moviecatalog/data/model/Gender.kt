package com.example.moviecatalog.data.model

enum class Gender(val value: Int) {
    MALE(1),
    FEMALE(0);

    companion object {
        fun fromValue(value: Int): Gender? {
            return entries.find { it.value == value }
        }
    }
}