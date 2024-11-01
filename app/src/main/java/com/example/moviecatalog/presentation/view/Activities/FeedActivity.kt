package com.example.moviecatalog.presentation.view.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ActivityFeedBinding
import com.example.moviecatalog.presentation.view.Fragments.FavoritesPlaceholderFragment
import com.example.moviecatalog.presentation.view.Fragments.FeedFragment
import com.example.moviecatalog.presentation.view.Fragments.MoviesFragment
import com.example.moviecatalog.presentation.view.Fragments.ProfileFragment

class FeedActivity : AppCompatActivity() {

    private var _binding: ActivityFeedBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private val feedFragment = FeedFragment()
    private val moviesFragment = MoviesFragment()
    private val profileFragment = ProfileFragment()
    private val favoritesFragment = FavoritesPlaceholderFragment()

    private var initialFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialFragment = intent.getSerializableExtra(EXTRA_INITIAL_FRAGMENT) as? Fragment
        setCurrentFragment(initialFragment ?: feedFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_feed -> setCurrentFragment(feedFragment)
                R.id.nav_movies -> setCurrentFragment(moviesFragment)
                R.id.nav_favorites -> setCurrentFragment(favoritesFragment)
                R.id.nav_profile -> setCurrentFragment(profileFragment)
                else -> false
            }
            true
        }

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.feedFragment -> {
                    binding.bottomNavigation.selectedItemId = R.id.nav_feed
                }
                R.id.moviesFragment -> {
                    binding.bottomNavigation.selectedItemId = R.id.nav_movies
                }
                R.id.favoritesPlaceholderFragment -> {
                    binding.bottomNavigation.selectedItemId = R.id.nav_favorites
                }
                R.id.profileFragment -> {
                    binding.bottomNavigation.selectedItemId = R.id.nav_profile
                }
            }
        }
    }

    private fun updateBottomNavigation() {
        val initialFragmentTag = intent.getStringExtra(EXTRA_INITIAL_FRAGMENT)
        initialFragment = when (initialFragmentTag) {
            "profile" -> profileFragment
            else -> feedFragment
        }
        binding.bottomNavigation.selectedItemId = when (initialFragment) {
            profileFragment -> R.id.nav_profile
            favoritesFragment -> R.id.nav_favorites
            moviesFragment -> R.id.nav_movies
            else -> R.id.nav_feed
        }
    }

    override fun onResume() {
        super.onResume()
        updateBottomNavigation()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

    companion object {
        const val EXTRA_INITIAL_FRAGMENT = "initial_fragment"
    }
}