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

class MoviesAdapter(
    private var movies: List<MovieElementModel>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<MovieElementModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.viewPagerImage)
        private val textViewTitle = itemView.findViewById<TextView>(R.id.viewPagerTitle)
        private val genreOne = itemView.findViewById<TextView>(R.id.genre_one)
        private val genreTwo = itemView.findViewById<TextView>(R.id.genre_two)
        private val genreThree = itemView.findViewById<TextView>(R.id.genre_three)

        fun bind(movie: MovieElementModel) {
            textViewTitle.text = movie.name

            Glide.with(itemView.context)
                .load(movie.poster)
                .transform(RoundedCorners(48))
                .into(imageView)

            for (i in movie.genres.indices) {
                when (i) {
                    0 -> {
                        genreOne.text = movie.genres[i].name
                    }
                    1 -> {
                        genreTwo.text = movie.genres[i].name
                    }
                    2 -> {
                        genreThree.text = movie.genres[i].name
                    }
                }
            }
        }
    }
}


