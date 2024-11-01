package com.example.moviecatalog.presentation.view.SignInScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.databinding.ActivitySignInBinding
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.backButton.setOnClickListener {
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