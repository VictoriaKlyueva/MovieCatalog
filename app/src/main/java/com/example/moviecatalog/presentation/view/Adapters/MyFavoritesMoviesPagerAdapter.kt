package com.example.moviecatalog.presentation.view.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel

class MyFavoritesMoviesPagerAdapter(
    private val context: Context,
    private val movies: List<MovieElementModel>
) : RecyclerView.Adapter<MyFavoritesMoviesPagerAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.myFavoriteMovieImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(context)
            .load(movie.poster)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = movies.size
}
