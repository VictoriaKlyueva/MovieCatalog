package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.usecase.GetMoviesFromPageUseCase
import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase

class FeedViewModel : ViewModel() {
    private val movieResponseRepository = MovieRepositoryImpl()
    private val getRandomMovieUseCase = GetRandomMovieUseCase(movieResponseRepository)

    private val _movie = MutableLiveData<MovieElementModel>()
    val movie: LiveData<MovieElementModel> get() = _movie

    fun getRandomMovie(callback: (MovieElementModel?, String?) -> Unit) {
        getRandomMovieUseCase.execute { randomMovie, error ->
            if (randomMovie != null) {
                _movie.postValue(randomMovie)
                callback(randomMovie, null)
            } else {
                callback(null, error)
            }
        }
    }
}

