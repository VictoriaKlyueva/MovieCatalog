package com.example.moviecatalog.presentation.view.SignInScreen

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
import com.example.moviecatalog.common.Constants.BINDING_IS_NOT_INITIALIZED
import com.example.moviecatalog.data.model.main.LoginCredentials
import com.example.moviecatalog.databinding.FragmentSignInBinding
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.view.utils.AlertHelper
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.presentation.viewModel.factory.SignInViewModelFactory
import com.example.moviecatalog.presentation.utils.EditTextHelper

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding ?:
    throw IllegalStateException(BINDING_IS_NOT_INITIALIZED)

    private lateinit var viewModel: SignInViewModel

    private val alertHelper = AlertHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupEditTextListeners()
        setupSignInButton()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SignInViewModelFactory(requireContext())
        )[SignInViewModel::class.java]
    }

    private fun setupEditTextListeners() {
        EditTextHelper.hideIcon(binding.editTextLogin)
        binding.editTextLogin.addTextChangedListener(
            EditTextHelper.createTextWatcher(
            binding.editTextLogin,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignInDataChanged(input, binding.editTextPassword.text.toString())
        })
        EditTextHelper.setClearTextOnIconTouch(binding.editTextLogin)

        EditTextHelper.hideIcon(binding.editTextPassword)
        val passwordIcon = if (
            binding.editTextPassword.transformationMethod ==
            PasswordTransformationMethod.getInstance()
            )
            R.drawable.ic_show_password
        else
            R.drawable.ic_hide_password

        binding.editTextPassword.addTextChangedListener(
            EditTextHelper.createTextWatcher(
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
            context?.let {
                binding.buttonSignIn.setTextColor(
                    if (isEnabled) ContextCompat.getColor(it, R.color.white)
                    else ContextCompat.getColor(it, R.color.gray_faded)
                )
                binding.buttonSignIn.setBackgroundResource(
                    if (isEnabled) R.drawable.button_primary_default
                    else R.drawable.button_secondary
                )
            }
        })

        binding.buttonSignIn.setOnClickListener {
            val user = LoginCredentials(
                username = binding.editTextLogin.text.toString(),
                password = binding.editTextPassword.text.toString()
            )
            viewModel.onSignInButtonClicked(user, {
                val intent = Intent(requireActivity(), FeedActivity::class.java)
                startActivity(intent)
            }, {
                alertHelper.showAlert(
                    requireContext(),
                    getString(R.string.error),
                    getString(R.string.incorrect_login_and_password)
                )
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
