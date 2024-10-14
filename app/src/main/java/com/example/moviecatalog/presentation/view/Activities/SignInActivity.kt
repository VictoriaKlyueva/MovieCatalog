package com.example.moviecatalog.presentation.view.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.viewModel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        val backButton = findViewById<ImageButton>(R.id.back_button)

        backButton.setOnClickListener {
            viewModel.onBackButtonClicked()
        }

        viewModel.navigateToWelcome.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                viewModel.onNavigated()
            }
        })
    }
}