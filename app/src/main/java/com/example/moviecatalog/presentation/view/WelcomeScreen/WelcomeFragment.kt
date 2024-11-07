package com.example.moviecatalog.presentation.view.WelcomeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.common.Constants.BINDING_IS_NOT_INITIALIZED
import com.example.moviecatalog.databinding.FragmentWelcomeBinding // Импортируйте сгенерированный класс binding
import com.example.moviecatalog.presentation.view.SignInScreen.SignInActivity
import com.example.moviecatalog.presentation.view.SignUpScreen.SignUpActivity
import com.example.moviecatalog.presentation.viewModel.factory.WelcomeViewModelFactory
import com.example.moviecatalog.presentation.viewmodels.WelcomeViewModel

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException(BINDING_IS_NOT_INITIALIZED)

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        setupViewModel()

        binding.loginButton.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }

        binding.registerButton.setOnClickListener {
            viewModel.onRegisterButtonClicked()
        }

        viewModel.goToSignIn.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(activity, SignInActivity::class.java))
                viewModel.resetSignInNavigation()
            }
        })

        viewModel.goToSignUp.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                startActivity(Intent(activity, SignUpActivity::class.java))
                viewModel.resetSignUpNavigation()
            }
        })

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            WelcomeViewModelFactory(requireContext())
        )[WelcomeViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
