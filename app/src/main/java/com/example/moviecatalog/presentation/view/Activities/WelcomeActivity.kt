package com.example.moviecatalog.presentation.view.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.SignUpActivity
import com.example.moviecatalog.presentation.viewmodels.WelcomeViewModel

class WelcomeActivity : AppCompatActivity() {

    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            welcomeViewModel.onLoginButtonClicked()
        }

        registerButton.setOnClickListener {
            welcomeViewModel.onRegisterButtonClicked()
        }

        welcomeViewModel.goToSignIn.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(this, SignInActivity::class.java))
                welcomeViewModel.resetSignInNavigation()
            }
        })

        welcomeViewModel.goToSignUp.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(this, SignUpActivity::class.java))
                welcomeViewModel.resetSignUpNavigation()
            }
        })
    }
}
