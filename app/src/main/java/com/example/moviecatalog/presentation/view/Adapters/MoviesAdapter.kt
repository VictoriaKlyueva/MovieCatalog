package com.example.moviecatalog.presentation.view.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel

class MoviesAdapter(private val movies: List<MovieElementModel>, private val onClick: (MovieElementModel) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val image: ImageView = itemView.findViewById(R.id.movie_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.name
        Glide.with(holder.itemView.context)
            .load(movie.poster)
            .transform(RoundedCorners(48))
            .into(holder.image)

        holder.itemView.setOnClickListener { onClick(movie) }
    }

    override fun getItemCount() = movies.size
}
