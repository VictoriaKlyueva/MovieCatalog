    package com.example.moviecatalog.presentation.viewModel

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.example.moviecatalog.data.model.MovieElementModel
    import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
    import com.example.moviecatalog.data.repository.MovieRepositoryImpl
    import com.example.moviecatalog.domain.usecase.GetFavoritesMoviesUseCase
    import com.example.moviecatalog.domain.usecase.GetMoviesFromPageUseCase
    import com.example.moviecatalog.domain.usecase.GetRandomMovieUseCase

    class MoviesViewModel : ViewModel() {
        private val movieRepository = MovieRepositoryImpl()
        private val movieResponseUseCase = GetMoviesFromPageUseCase(movieRepository)
        private val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)

        private val favoritesMoviesRepository = FavoriteMoviesRepositoryImpl()
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
    }
