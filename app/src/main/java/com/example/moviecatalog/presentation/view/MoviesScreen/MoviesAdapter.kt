package com.example.moviecatalog.presentation.view.MoviesScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.databinding.ItemMovieEnhancedBinding
import com.example.moviecatalog.presentation.view.MovieDetailsScreen.MovieDetailsActivity

class MoviesAdapter(
    private var movies: List<MovieElementModel>,
    private val onWatchButtonClick: (MovieElementModel) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieEnhancedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, parent.context)
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

    inner class MovieViewHolder(private val binding: ItemMovieEnhancedBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieElementModel) {
            binding.viewPagerTitle.text = movie.name
            if (binding.viewPagerTitle.text.length > 50) {
                binding.viewPagerTitle.layoutParams.width =
                    ConstraintLayout.LayoutParams.MATCH_PARENT
            }

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
                goToMovieScreen(movie)
            }
        }

        private fun goToMovieScreen(movie: MovieElementModel) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            context.startActivity(intent)
        }

        private fun hideGenres() {
            binding.genreOne.visibility = View.GONE
            binding.genreTwo.visibility = View.GONE
            binding.genreThree.visibility = View.GONE
        }
    }
}