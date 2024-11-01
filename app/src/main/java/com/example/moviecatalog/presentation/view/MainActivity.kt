package com.example.moviecatalog.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.navigateToWelcome.observe(this, Observer { navigate ->
            if (navigate) {
                navigateToWelcomeActivity()
                viewModel.onNavigationComplete()
            }
        })
    }

    private fun navigateToWelcomeActivity() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
}
