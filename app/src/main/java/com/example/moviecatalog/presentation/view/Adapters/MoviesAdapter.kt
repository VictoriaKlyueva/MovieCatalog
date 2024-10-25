package com.example.moviecatalog.presentation.view.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.ItemMovieBinding

class MoviesAdapter(
    private var movies: List<MovieElementModel>,
    private val onWatchButtonClick: (MovieElementModel) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<MovieElementModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieElementModel) {
            binding.viewPagerTitle.text = movie.name

            Glide.with(binding.root.context)
                .load(movie.poster)
                .transform(RoundedCorners(48))
                .into(binding.viewPagerImage)

            hideGenres()
            movie.genres.take(3).forEachIndexed { index, genre ->
                when (index) {
                    0 -> {
                        binding.genreOne.text = genre.name
                        binding.genreOne.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.genreTwo.text = genre.name
                        binding.genreTwo.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.genreThree.text = genre.name
                        binding.genreThree.visibility = View.VISIBLE
                    }
                }
            }

            binding.watchButton.setOnClickListener {
                onWatchButtonClick(movie)
            }
        }

        private fun hideGenres() {
            binding.genreOne.visibility = View.GONE
            binding.genreTwo.visibility = View.GONE
            binding.genreThree.visibility = View.GONE
        }
    }
}