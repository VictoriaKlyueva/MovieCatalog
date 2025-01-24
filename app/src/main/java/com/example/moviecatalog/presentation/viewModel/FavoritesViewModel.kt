package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.MOVIE_RECEIVING_ERROR
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.domain.usecase.FetchFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.FetchFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.RemoveGenreUseCase
import kotlinx.coroutines.launch

class FavoritesViewModel(
    context: Context
) : ViewModel() {
    private val favoritesMoviesRepository = FavoriteMoviesRepositoryImpl()
    private val userDataSource = UserDataSource(context)

    private val fetchFavoriteGenresUseCase = FetchFavoriteGenresUseCase(userDataSource)
    private val removeGenreUseCase = RemoveGenreUseCase(userDataSource)
    private val fetchFavoriteMoviesUseCase = FetchFavoriteMoviesUseCase(favoritesMoviesRepository)

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    private val _favoritesMovies = MutableLiveData<List<MovieElementModel>?>()
    val favoritesMovies: LiveData<List<MovieElementModel>?> get() = _favoritesMovies

    private val _navigateToPlaceholder = MutableLiveData<Boolean>()
    val navigateToPlaceholder: LiveData<Boolean> get() = _navigateToPlaceholder

    private fun checkIfEmpty() {
        _navigateToPlaceholder.postValue(
            _favoritesGenres.value.isNullOrEmpty() && _favoritesMovies.value.isNullOrEmpty()
        )
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            val genres = fetchFavoriteGenresUseCase.execute()
            _favoritesGenres.postValue(genres)

            fetchFavoriteMoviesUseCase.execute { movies, error ->
                if (error == null) {
                    _favoritesMovies.postValue(movies ?: emptyList())
                } else {
                    println(MOVIE_RECEIVING_ERROR + "$error")
                    _favoritesMovies.postValue(emptyList())
                }

                checkIfEmpty()
            }
        }
    }

    fun resetNavigation() {
        _navigateToPlaceholder.postValue(false)
    }

    fun fetchFavoritesGenres() {
        viewModelScope.launch {
            val genres = fetchFavoriteGenresUseCase.execute()
            _favoritesGenres.postValue(genres)
            checkIfEmpty()
        }
    }

    fun fetchFavoritesMovies() {
        fetchFavoriteMoviesUseCase.execute { movies, error ->
            if (error == null) {
                if (movies != null) {
                    _favoritesMovies.postValue(movies)
                } else {
                    _favoritesMovies.postValue(emptyList())
                }
            } else {
                println(MOVIE_RECEIVING_ERROR + "$error")
                _favoritesMovies.postValue(emptyList())
            }
            checkIfEmpty()
        }
    }
}
