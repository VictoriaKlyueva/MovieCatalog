    package com.example.moviecatalog.presentation.viewModel

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.example.moviecatalog.data.model.MovieElementModel
    import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
    import com.example.moviecatalog.data.repository.MovieRepositoryImpl
    import com.example.moviecatalog.domain.usecase.GetFavoritesMoviesUseCase
    import com.example.moviecatalog.domain.usecase.GetMoviesFromPageUseCase

    class MoviesViewModel : ViewModel() {
        private val movieResponseRepository = MovieRepositoryImpl()
        private val movieResponseUseCase = GetMoviesFromPageUseCase(movieResponseRepository)

        private val favoritesMoviesRepository = FavoriteMoviesRepositoryImpl()
        private val favoritesMoviesUseCase = GetFavoritesMoviesUseCase(favoritesMoviesRepository)

        private val _movies = MutableLiveData<List<MovieElementModel>>()
        val movies: LiveData<List<MovieElementModel>> get() = _movies

        private val _favoritesMovies = MutableLiveData<List<MovieElementModel>>()
        val favoritesMovies: LiveData<List<MovieElementModel>> get() = _favoritesMovies

        fun fetchMovies(page: Int = (1..5).random()) {
            movieResponseUseCase.execute(page) { movies, error ->
                if (error == null) {
                    _movies.postValue(movies?.take(5) ?: emptyList())
                } else {
                    println("Ошибка получения данных: $error")
                    _movies.postValue(emptyList())
                }
            }
        }

        fun fetchFavoriteMovies() {
            /*
            favoritesMoviesUseCase.execute { movies, error ->
                if (error == null) {
                    _favoritesMovies.postValue(movies ?: emptyList())
                } else {
                    _favoritesMovies.postValue(emptyList())
                }
            }
            */
            val page = (1..5).random()
            movieResponseUseCase.execute(page) { movies, error ->
                if (error == null) {
                    _favoritesMovies.postValue(movies?.take(5) ?: emptyList())
                } else {
                    println("Ошибка получения данных: $error")
                    _favoritesMovies.postValue(emptyList())
                }
            }
        }

        fun getRandomMovie(movies: List<MovieElementModel>): MovieElementModel {
            val randomIndex = (movies.indices).random()
            return movies[randomIndex]
        }
    }
