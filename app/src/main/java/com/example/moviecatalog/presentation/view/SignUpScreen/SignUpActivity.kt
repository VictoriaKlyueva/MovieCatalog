package com.example.moviecatalog.presentation.view.SignUpScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.databinding.ActivitySignUpBinding
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding ?:
    throw IllegalStateException("Binding is not initialized")

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.backButton.setOnClickListener {
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