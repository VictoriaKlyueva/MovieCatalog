package com.example.moviecatalog.presentation.view.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_sign_up)

        val backButton = findViewById<ImageButton>(R.id.back_button)

        backButton.setOnClickListener {
            signUpViewModel.onBackButtonClicked()
        }

        signUpViewModel.navigateToWelcome.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                signUpViewModel.onNavigationDone()
            }
        })
    }
}