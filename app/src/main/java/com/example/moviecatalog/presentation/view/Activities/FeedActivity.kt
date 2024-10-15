package com.example.moviecatalog.presentation.view.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.moviecatalog.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_feed -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }
                R.id.nav_movies -> {
                    navController.navigate(R.id.moviesFragment)
                    true
                }
                R.id.nav_favorites -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}
