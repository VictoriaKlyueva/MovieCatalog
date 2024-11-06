package com.example.moviecatalog.presentation.view.MoviesScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.MovieElementModel
import com.example.moviecatalog.databinding.ItemMovieSimpleBinding
import com.example.moviecatalog.presentation.view.MovieDetailsScreen.MovieDetailsActivity
import com.example.moviecatalog.presentation.viewModel.MoviesViewModel

class AllMoviesAdapter(
    private var movies: List<MovieElementModel>,
    private val viewModel: MoviesViewModel,
) : RecyclerView.Adapter<AllMoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieSimpleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    private fun goToMovieScreen(movieId: String, context: Context) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movieId)
        context.startActivity(intent)
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieSimpleBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("DefaultLocale")
        fun bind(movie: MovieElementModel) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .transform(RoundedCorners(8))
                .into(binding.movieFromAllImageView)

            binding.movieFromAllImageView.setOnClickListener {
                goToMovieScreen(movie.id, context)
            }

            viewModel.isFavorite(movie) { isFavorite ->
                binding.heartContainer.visibility =
                    if (isFavorite)
                        View.VISIBLE
                    else
                        View.GONE
            }

            // Rating
            val averageRating = movie.reviews.map { it.rating }.average()
            binding.rating.text = String.format("%.1f", averageRating)

            val ratingBackground = when {
                averageRating >= 9 -> R.drawable.rating_nine
                averageRating >= 8 -> R.drawable.rating_eight
                averageRating >= 7 -> R.drawable.rating_seven
                averageRating >= 6 -> R.drawable.rating_six
                averageRating >= 5 -> R.drawable.rating_five
                averageRating >= 4 -> R.drawable.rating_four
                averageRating >= 3 -> R.drawable.rating_three
                averageRating >= 2 -> R.drawable.rating_two
                averageRating >= 1 -> R.drawable.rating_one
                else -> R.drawable.button_primary_default
            }

            val ratingTextColor = when {
                averageRating in (5.0 .. 7.0) && averageRating != 5.0 && averageRating != 7.0 ->
                    R.color.dark_faded
                else ->
                    android.graphics.Color.WHITE
            }

            binding.rating.setBackgroundResource(ratingBackground)
            binding.rating.setTextColor(ratingTextColor)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MovieElementModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
