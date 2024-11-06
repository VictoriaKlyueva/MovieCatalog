package com.example.moviecatalog.presentation.view.WelcomeScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.view.SignInScreen.SignInActivity
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.presentation.viewModel.factory.SignInViewModelFactory
import com.example.moviecatalog.presentation.viewModel.factory.WelcomeViewModelFactory
import com.example.moviecatalog.presentation.viewmodels.WelcomeViewModel

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        setupViewModel()

        if (viewModel.isAuthorized()) {
            navigateToFeedScreen()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            WelcomeViewModelFactory(this)
        )[WelcomeViewModel::class.java]
    }

    private fun navigateToFeedScreen() {
        startActivity(Intent(this, FeedActivity::class.java))
        finish()
    }
}
