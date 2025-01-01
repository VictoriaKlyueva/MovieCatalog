package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.model.main.ReviewModifyModel

interface ReviewRepository {
    fun postReview(
        movieId: String,
        reviewModify: ReviewModifyModel,
        callback: (String?) -> Unit
    )

    fun putReview(
        movieId: String,
        reviewId: String,
        reviewModify: ReviewModifyModel,
        callback: (String?) -> Unit
    )

    fun deleteReview(
        movieId: String,
        reviewId: String,
        callback: (String?) -> Unit
    )
}