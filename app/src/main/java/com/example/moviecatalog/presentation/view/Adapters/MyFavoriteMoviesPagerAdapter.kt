package com.example.moviecatalog.presentation.view.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.MyFavoriteMovieBinding

class MyFavoriteMoviesPagerAdapter(
    private var movies: List<MovieElementModel>
) : RecyclerView.Adapter<MyFavoriteMoviesPagerAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(
        private val binding: MyFavoriteMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieElementModel) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .transform(RoundedCorners(48))
                .into(binding.myFavoriteMovieImageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MyFavoriteMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MovieElementModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size
}
