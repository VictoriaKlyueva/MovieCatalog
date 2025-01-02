package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.EMPTY_STRING
import com.example.moviecatalog.data.datasource.UserDataSource
import com.example.moviecatalog.data.model.kinopoisk.FilmSearchByFiltersResponse_items
import com.example.moviecatalog.data.model.main.GenreModel
import com.example.moviecatalog.data.model.main.MovieDetailsModel
import com.example.moviecatalog.data.model.main.ReviewModel
import com.example.moviecatalog.data.model.main.ReviewModifyModel
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.data.repository.FavoriteMoviesRepositoryImpl
import com.example.moviecatalog.data.repository.KinopoiskRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.repository.ReviewRepositoryImpl
import com.example.moviecatalog.data.repository.UserRepositoryImpl
import com.example.moviecatalog.domain.usecase.AddFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.AddFavoriteUseCase
import com.example.moviecatalog.domain.usecase.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.CheckFavoriteMovieUseCase
import com.example.moviecatalog.domain.usecase.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.EditReviewUseCase
import com.example.moviecatalog.domain.usecase.FetchFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.FetchFriendsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieByNameUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.domain.usecase.RemoveFavoriteMovieUseCase
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    context: Context
) : ViewModel() {
    private val movieRepository = MovieRepositoryImpl()
    private val favoritesRepository = FavoriteMoviesRepositoryImpl()
    private val kinopoiskRepository = KinopoiskRepositoryImpl()
    private val reviewRepository = ReviewRepositoryImpl()


    private val userDataSource = UserDataSource(context)

    private val userRepository = UserRepositoryImpl(userDataSource)

    private val fetchFavoriteGenresUseCase = FetchFavoriteGenresUseCase(userDataSource)
    private val fetchFriendsUseCase = FetchFriendsUseCase(userDataSource)
    private val addFriendUseCase = AddFriendUseCase(userDataSource)
    private val movieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private val addFavoriteUseCase = AddFavoriteUseCase(favoritesRepository)
    private val addFavoriteGenresUseCase = AddFavoriteGenresUseCase(userDataSource)
    private val checkFavoriteMovieUseCase = CheckFavoriteMovieUseCase(favoritesRepository)
    private val removeFavoriteMovieUseCase = RemoveFavoriteMovieUseCase(favoritesRepository)
    private val getMovieByNameUseCase = GetMovieByNameUseCase(kinopoiskRepository)
    private val addReviewUseCase = AddReviewUseCase(reviewRepository)
    private val editReviewUseCase = EditReviewUseCase(reviewRepository)
    private val deleteReviewUseCase = DeleteReviewUseCase(reviewRepository)
    private val getProfileUseCase = GetProfileUseCase(userRepository)

    private val _movie = MutableLiveData<MovieDetailsModel?>()
    val movie: LiveData<MovieDetailsModel?> get() = _movie

    private val _movieEnhanced = MutableLiveData<FilmSearchByFiltersResponse_items?>()
    val movieEnhanced: LiveData<FilmSearchByFiltersResponse_items?> get() = _movieEnhanced

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _favoritesGenres = MutableLiveData<List<GenreModel>>()
    val favoritesGenres: LiveData<List<GenreModel>> get() = _favoritesGenres

    private val _friends = MutableLiveData<List<UserShortModel>>()
    val friends: LiveData<List<UserShortModel>> get() = _friends

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun toggleFavorite() {
        _isFavorite.value = !(_isFavorite.value ?: false)
    }

    fun fetchFriends() {
        viewModelScope.launch {
            val friends = fetchFriendsUseCase.execute()
            _friends.postValue(friends)
        }
    }

    fun countFriendsWhoLikedMovie(reviews: List<ReviewModel>): Int {
        val friendUserIds = _friends.value?.map { it.userId } ?: emptyList()
        return reviews.count { review ->
            review.author?.userId?.let { it in friendUserIds } == true && review.rating > 6
        }
    }

    fun addGenreToFavorites(genre: GenreModel) {
        viewModelScope.launch {
            addFavoriteGenresUseCase.execute(genre)
        }
    }

    fun fetchFavoritesGenres() {
        viewModelScope.launch {
            val genres = fetchFavoriteGenresUseCase.execute()
            _favoritesGenres.postValue(genres)
        }
    }

    fun addFriend(friend: UserShortModel?) {
        if (friend != null) {
            viewModelScope.launch {
                addFriendUseCase.execute(friend)
            }
        }
    }

    private fun checkFavorite(movieId: String) {
        checkFavoriteMovieUseCase.execute(movieId) { isFavoriteMovie, error ->
            if (error != null) {
                _errorMessage.postValue(error)
                return@execute
            }
            _isFavorite.postValue(isFavoriteMovie)
        }
    }

    private fun getMovieByName() {
        _movie.value?.let { movieDetails ->
            val movieName = movieDetails.name ?: EMPTY_STRING
            getMovieByNameUseCase.execute(movieName) { movie, error ->

                if (error != null) {
                    println("Ошибка: $error")
                    _errorMessage.postValue(error)
                } else {
                    println("Найденный фильм: $movie")
                    _movieEnhanced.postValue(movie)
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }

    fun fetchMovie(movieId: String) {
        movieDetailsUseCase.execute(movieId) { details, error ->
            if (details != null) {
                _movie.value = details
                _errorMessage.value = null
                getMovieByName()
            } else {
                _movie.value = null
                _errorMessage.value = error
            }
        }

        checkFavorite(movieId)
    }

    fun removeFavorite() {
        _movie.value?.let { movieDetails ->
            val movieId = movieDetails.id
            removeFavoriteMovieUseCase.execute(movieId) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Успешно удалено из избранного")
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }

    fun addToFavorite() {
        _movie.value?.let { movieDetails ->
            val movieId = movieDetails.id
            addFavoriteUseCase.execute(movieId) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Успешно добавлено в избранное")
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }

    fun addReview(review: ReviewModifyModel) {
        _movie.value?.let { movieDetails ->
            val movieId = movieDetails.id
            addReviewUseCase.execute(movieId, review) { error ->
                if (error != null) {
                    println("Ошибка отправки запроса: $error")
                } else {
                    println("Отзыв создан успешно")
                }
            }
        } ?: run {
            println("Фильм не доступен")
        }
    }

    fun findUserReview(callback: (String?) -> Unit) {
        _movie.value?.let { movieDetails ->
            getProfileUseCase.execute { receivedProfile, _ ->
                if (receivedProfile != null) {
                    var foundReview: String? = null
                    for (review in movieDetails.reviews) {
                        if (review.author?.userId == receivedProfile.id) {
                            foundReview = review.id
                            break
                        }
                    }
                    callback(foundReview)
                } else {
                    callback(null)
                }
            }
        } ?: run {
            callback(null)
        }
    }

    fun editReview(review: ReviewModifyModel) {
        findUserReview { userId ->
            _movie.value?.let { movieDetails ->
                val movieId = movieDetails.id
                if (userId != null) {
                    editReviewUseCase.execute(movieId, userId, review) { error ->
                        if (error != null) {
                            println("Ошибка отправки запроса: $error")
                        } else {
                            println("Отзыв изменен успешно")
                        }
                    }
                } else {
                    println("Отзыв пользователя не найден")
                }
            } ?: run {
                println("Фильм не доступен")
            }
        }
    }

    fun deleteReview() {
        findUserReview { userId ->
            _movie.value?.let { movieDetails ->
                val movieId = movieDetails.id
                if (userId != null) {
                    deleteReviewUseCase.execute(movieId, userId) { error ->
                        if (error != null) {
                            println("Ошибка отправки запроса: $error")
                        } else {
                            println("Отзыв удален успешно")
                        }
                    }
                } else {
                    println("Отзыв пользователя не найден")
                }
            } ?: run {
                println("Фильм не доступен")
            }
        }
    }
}

