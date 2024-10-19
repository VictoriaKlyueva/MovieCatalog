package com.example.moviecatalog.presentation.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FragmentWelcomeBinding // Импортируйте сгенерированный класс binding
import com.example.moviecatalog.presentation.view.Activities.SignInActivity
import com.example.moviecatalog.presentation.view.Activities.SignUpActivity
import com.example.moviecatalog.presentation.viewmodels.WelcomeViewModel

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        welcomeViewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]

        binding.loginButton.setOnClickListener {
            welcomeViewModel.onLoginButtonClicked()
        }

        binding.registerButton.setOnClickListener {
            welcomeViewModel.onRegisterButtonClicked()
        }

        welcomeViewModel.goToSignIn.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(activity, SignInActivity::class.java))
                welcomeViewModel.resetSignInNavigation()
            }
        })

        welcomeViewModel.goToSignUp.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(activity, SignUpActivity::class.java))
                welcomeViewModel.resetSignUpNavigation()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
