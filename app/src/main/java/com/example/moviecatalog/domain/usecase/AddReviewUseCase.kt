package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.data.repository.ReviewRepositoryImpl

class AddReviewUseCase(private val reviewRepository: ReviewRepositoryImpl) {
    fun execute(movieId: String, review: ReviewModifyModel, callback: (String?) -> Unit) {
        reviewRepository.postReview(movieId, review, callback)
    }
}