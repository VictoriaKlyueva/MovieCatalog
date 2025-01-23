package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.kinopoisk.PersonByNameResponse_items
import com.example.moviecatalog.domain.repository.KinopoiskRepository

class GetDirectorByNameUseCase(private val kinopoiskRepository: KinopoiskRepository) {
    fun execute(
        name: String,
        callback: (PersonByNameResponse_items?, String?) -> Unit
    ) {
        kinopoiskRepository.getDirector(name, page = 1) { directors, error ->
            if (error != null) {
                callback(null, error)
            } else {
                if (!directors.isNullOrEmpty()) {
                    callback(directors[0], null)
                } else {
                    callback(null, "No directors found")
                }
            }
        }
    }
}