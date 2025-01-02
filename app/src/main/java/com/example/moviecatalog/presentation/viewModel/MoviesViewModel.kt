package com.example.moviecatalog.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.common.Constants.MOVIE_RECEIVING_ERROR
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImplImpl
import com.example.moviecatalog.domain.common.Constants.TOTAL_PAGES
import com.example.moviecatalog.domain.usecase.CheckFavoriteMovieUseCase
import com.example.moviecatalog.domain.usecase.GetFavoritesMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetMoviesFromPageUseCase
import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase

class MoviesViewModel : ViewModel() {
    private val movieRepository = MovieRepositoryImplImpl()
    private val movieResponseUseCase = GetMoviesFromPageUseCase(movieRepository)
    private val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)

    private val favoritesMoviesRepository = FavoriteMoviesRepositoryImpl()
    private val checkFavoriteMovieUseCase = CheckFavoriteMovieUseCase(favoritesMoviesRepository)
    private val favoritesMoviesUseCase = GetFavoritesMoviesUseCase(favoritesMoviesRepository)

    private val _movies = MutableLiveData<List<MovieElementModel>>()
    val movies: LiveData<List<MovieElementModel>> get() = _movies

    private val _favoritesMovies = MutableLiveData<List<MovieElementModel>>()
    val favoritesMovies: LiveData<List<MovieElementModel>> get() = _favoritesMovies

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

    fun isFavorite(movie: MovieElementModel, callback: (Boolean) -> Unit) {
        checkFavoriteMovieUseCase.execute(movie.id) { isFavorite, _ ->
            callback(isFavorite)
        }
    }

    fun fetchMovies(page: Int = (1..TOTAL_PAGES).random(), isExcludeFirstFive: Boolean = false) {
        movieResponseUseCase.execute(page) { movies, error ->
            if (error == null) {
                _movies.postValue(
                    if (isExcludeFirstFive)
                        movies?.slice(5..5) ?: emptyList()
                    else
                        movies ?: emptyList()
                )
            } else {
                println("$MOVIE_RECEIVING_ERROR $error")
                _movies.postValue(emptyList())
            }
        }
    }

    fun fetchFavoriteMovies() {
        favoritesMoviesUseCase.execute { movies, error ->
            if (error == null) {
                _favoritesMovies.postValue(movies ?: emptyList())
            } else {
                println("$MOVIE_RECEIVING_ERROR $error")
                _favoritesMovies.postValue(emptyList())
            }
        }
    }
}
