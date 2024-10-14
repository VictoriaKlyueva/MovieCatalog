package com.example.moviecatalog.presentation.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.view.Activities.SignInActivity
import com.example.moviecatalog.presentation.view.Activities.SignUpActivity
import com.example.moviecatalog.presentation.viewmodels.WelcomeViewModel

class WelcomeFragment : Fragment() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private val welcomeViewModel: WelcomeViewModel by activityViewModels() // Используем activityViewModels для совместного использования ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        loginButton = view.findViewById(R.id.loginButton)
        registerButton = view.findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            welcomeViewModel.onLoginButtonClicked()
        }

        registerButton.setOnClickListener {
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

        return view
    }
}