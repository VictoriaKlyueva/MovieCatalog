package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.MovieRepositoryImplImpl
import com.example.moviecatalog.domain.usecase.FetchFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase
import kotlinx.coroutines.launch

class FeedViewModel(context: Context) : ViewModel() {
    private val movieRepository = MovieRepositoryImplImpl()
    private val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)

    private val userDataSource = UserDataSource(context)
    private val fetchFavoriteGenresUseCase = FetchFavoriteGenresUseCase(userDataSource)

    private val _movie = MutableLiveData<MovieElementModel>()
    val movie: LiveData<MovieElementModel> get() = _movie

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    fun fetchFavoritesGenres() {
        viewModelScope.launch {
            val genres = fetchFavoriteGenresUseCase.execute()
            _favoritesGenres.postValue(genres)
        }
    }

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

