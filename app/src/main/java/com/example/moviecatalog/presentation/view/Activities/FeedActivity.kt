package com.example.moviecatalog.presentation.view.Activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.view.FavoritesFragment
import com.example.moviecatalog.presentation.view.Fragments.FeedFragment
import com.example.moviecatalog.presentation.view.Fragments.MoviesFragment
import com.example.moviecatalog.presentation.view.Fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_feed -> {
                    item.setIcon(R.drawable.ic_feed_active)
                    bottomNavigationView.menu.findItem(R.id.nav_movies)
                        .setIcon(R.drawable.ic_movies)
                    bottomNavigationView.menu.findItem(R.id.nav_favorites)
                        .setIcon(R.drawable.ic_favorites)
                    bottomNavigationView.menu.findItem(R.id.nav_profile)
                        .setIcon(R.drawable.ic_profile)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_movies -> {
                    item.setIcon(R.drawable.ic_movies_active)
                    bottomNavigationView.menu.findItem(R.id.nav_feed).setIcon(R.drawable.ic_feed)
                    bottomNavigationView.menu.findItem(R.id.nav_favorites)
                        .setIcon(R.drawable.ic_favorites)
                    bottomNavigationView.menu.findItem(R.id.nav_profile)
                        .setIcon(R.drawable.ic_profile)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_favorites -> {
                    item.setIcon(R.drawable.ic_favorites_active)
                    bottomNavigationView.menu.findItem(R.id.nav_feed).setIcon(R.drawable.ic_feed)
                    bottomNavigationView.menu.findItem(R.id.nav_movies)
                        .setIcon(R.drawable.ic_movies)
                    bottomNavigationView.menu.findItem(R.id.nav_profile)
                        .setIcon(R.drawable.ic_profile)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_profile -> {
                    item.setIcon(R.drawable.ic_profile_active)
                    bottomNavigationView.menu.findItem(R.id.nav_feed).setIcon(R.drawable.ic_feed)
                    bottomNavigationView.menu.findItem(R.id.nav_movies)
                        .setIcon(R.drawable.ic_movies)
                    bottomNavigationView.menu.findItem(R.id.nav_favorites)
                        .setIcon(R.drawable.ic_favorites)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        val feedFragment = FeedFragment()
        val moviesFragment = MoviesFragment()
        val profileFragment = ProfileFragment()
        val favoritesFragment = FavoritesFragment()

        setCurrentFragment(feedFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_feed->setCurrentFragment(feedFragment)
                R.id.nav_movies->setCurrentFragment(moviesFragment)
                R.id.nav_favorites->setCurrentFragment(favoritesFragment)
                R.id.nav_profile->setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }
}
