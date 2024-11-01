package com.example.moviecatalog.presentation.view.MoviesScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.ItemMyFavoriteMovieBinding

class MyFavoriteMoviesAdapter(
    private var movies: List<MovieElementModel>
) : RecyclerView.Adapter<MyFavoriteMoviesAdapter.MovieViewHolder>() {

    private var firstVisiblePositions: MutableSet<Int> = mutableSetOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setFirstVisiblePosition(position: Int) {
        firstVisiblePositions.clear()
        firstVisiblePositions.add(position)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        val binding: ItemMyFavoriteMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieElementModel, isFirstVisible: Boolean) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .transform(RoundedCorners(24))
                .into(binding.myFavoriteMovieImageView)

            val scale = if (isFirstVisible) 1.1f else 1.0f

            binding.myFavoriteMovieImageView.scaleX = scale
            binding.myFavoriteMovieImageView.scaleY = scale
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = ItemMyFavoriteMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, firstVisiblePositions.contains(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MovieElementModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size
}


