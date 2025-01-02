package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.repository.ReviewRepositoryImpl

class DeleteReviewUseCase(private val reviewRepository: ReviewRepositoryImpl) {
    fun execute(
        movieId: String,
        reviewId: String,
        callback: (String?) -> Unit
    ) {
        reviewRepository.deleteReview(movieId, reviewId, callback)
    }
}