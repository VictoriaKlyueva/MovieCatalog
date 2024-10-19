package com.example.moviecatalog.presentation.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.LoginCredentials
import com.example.moviecatalog.databinding.FragmentSignInBinding
import com.example.moviecatalog.presentation.view.Activities.FeedActivity
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.utils.EditTextHelper

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        setupEditTextListeners()
        setupSignInButton()
        
        return binding.root
    }

    private fun setupEditTextListeners() {
        EditTextHelper.hideIcon(binding.editTextLogin)
        binding.editTextLogin.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextLogin,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignInDataChanged(input, binding.editTextPassword.text.toString())
        })
        EditTextHelper.setClearTextOnIconTouch(binding.editTextLogin)

        EditTextHelper.hideIcon(binding.editTextPassword)
        val passwordIcon = if (binding.editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
            R.drawable.ic_show_password
        else
            R.drawable.ic_hide_password

        binding.editTextPassword.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextPassword,
            passwordIcon
        ) { input ->
            viewModel.onSignInDataChanged(binding.editTextLogin.text.toString(), input)
        })
        EditTextHelper.setHidePasswordOnIconTouch(binding.editTextPassword)
    }

    private fun setupSignInButton() {
        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            binding.buttonSignIn.isEnabled = isEnabled
            binding.buttonSignIn.setTextColor(
                if (isEnabled)
                    ContextCompat.getColor(requireContext(), R.color.white)
                else ContextCompat.getColor(requireContext(), R.color.gray_faded)
            )
            binding.buttonSignIn.setBackgroundResource(
                if (isEnabled)
                    R.drawable.button_primary_default
                else
                    R.drawable.button_secondary
            )
        })

        binding.buttonSignIn.setOnClickListener {
            val user = LoginCredentials(
                username = binding.editTextLogin.text.toString(),
                password = binding.editTextPassword.text.toString()
            )
            viewModel.onSignInButtonClicked(user)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
