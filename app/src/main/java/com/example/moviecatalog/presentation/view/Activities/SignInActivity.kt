package com.example.moviecatalog.presentation.view.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ActivityFeedBinding
import com.example.moviecatalog.databinding.ActivitySignInBinding
import com.example.moviecatalog.presentation.viewModel.MainViewModel
import com.example.moviecatalog.presentation.viewModel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding ?:
    throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

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