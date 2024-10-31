package com.example.moviecatalog.presentation.view.Activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.view.FavoritesFragment
import com.example.moviecatalog.presentation.view.Fragments.FavoritesPlaceholderFragment
import com.example.moviecatalog.presentation.view.Fragments.FeedFragment
import com.example.moviecatalog.presentation.view.Fragments.MoviesFragment
import com.example.moviecatalog.presentation.view.Fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FeedActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val feedFragment = FeedFragment()
    private val moviesFragment = MoviesFragment()
    private val profileFragment = ProfileFragment()
    private val favoritesFragment = FavoritesPlaceholderFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        setCurrentFragment(feedFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_feed -> setCurrentFragment(feedFragment)
                R.id.nav_movies -> setCurrentFragment(moviesFragment)
                R.id.nav_favorites -> setCurrentFragment(favoritesFragment)
                R.id.nav_profile -> setCurrentFragment(profileFragment)
                else -> false
            }
            true
        }

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.feedFragment -> {
                    bottomNavigationView.selectedItemId = R.id.nav_feed
                }
                R.id.moviesFragment -> {
                    bottomNavigationView.selectedItemId = R.id.nav_movies
                }
                R.id.favoritesPlaceholderFragment -> {
                    bottomNavigationView.selectedItemId = R.id.nav_favorites
                }
                R.id.profileFragment -> {
                    bottomNavigationView.selectedItemId = R.id.nav_profile
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) != fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }
}

