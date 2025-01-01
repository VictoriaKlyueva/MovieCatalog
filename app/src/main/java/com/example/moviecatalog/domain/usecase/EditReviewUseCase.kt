package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.data.repository.ReviewRepositoryImpl

class EditReviewUseCase(private val reviewRepository: ReviewRepositoryImpl) {
    fun execute(
        movieId: String,
        reviewId: String,
        review: ReviewModifyModel,
        callback: (String?) -> Unit
    ) {
        reviewRepository.putReview(movieId, reviewId, review, callback)
    }
}