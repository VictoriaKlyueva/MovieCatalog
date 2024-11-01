package com.example.moviecatalog.presentation.view.SignUpScreen

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.Gender
import com.example.moviecatalog.data.model.UserRegisterModel
import com.example.moviecatalog.databinding.FragmentSignUpBinding
import com.example.moviecatalog.presentation.utils.DateHelper
import com.example.moviecatalog.presentation.utils.GenderToggleHandler
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel
import com.example.moviecatalog.utils.EditTextHelper

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private lateinit var genderToggleHandler: GenderToggleHandler
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        EditTextHelper.hideIcon(binding.editTextLogin)
        binding.editTextLogin.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextLogin,
            R.drawable.ic_clear
        ) { input ->
            updateViewModelData(input)
        })
        EditTextHelper.setClearTextOnIconTouch(binding.editTextLogin)

        EditTextHelper.hideIcon(binding.editTextEmail)
        binding.editTextEmail.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextEmail,
            R.drawable.ic_clear
        ) { input ->
            updateViewModelData(input = input, email = true)
        })
        EditTextHelper.setClearTextOnIconTouch(binding.editTextEmail)

        EditTextHelper.hideIcon(binding.editTextName)
        binding.editTextName.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextName,
            R.drawable.ic_clear
        ) { input ->
            updateViewModelData(input = input, name = true)
        })
        EditTextHelper.setClearTextOnIconTouch(binding.editTextName)

        val passwordIcon =
            if (binding.editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        binding.editTextPassword.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextPassword,
            passwordIcon
        ) { input ->
            updateViewModelData(input = input, password = true)
        })
        EditTextHelper.setHidePasswordOnIconTouch(binding.editTextPassword)

        val confirmPasswordIcon =
            if (binding.editTextConfirmPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        binding.editTextConfirmPassword.addTextChangedListener(EditTextHelper.createTextWatcher(
            binding.editTextConfirmPassword,
            confirmPasswordIcon
        ) { input ->
            updateViewModelData(input = input, confirmPassword = true)
        })
        EditTextHelper.setHidePasswordOnIconTouch(binding.editTextConfirmPassword)

        val dateHelper = DateHelper()
        binding.editTextDateOfBirth.setOnClickListener {
            dateHelper.showDatePickerDialog(requireContext(), binding.editTextDateOfBirth)
        }
        binding.editTextDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateViewModelData()
            }

            override fun afterTextChanged(s: Editable?) {
                val dateText = s.toString().trim()
                dateHelper.updateDateDrawable(binding.editTextDateOfBirth, dateText)
            }
        })

        genderToggleHandler = GenderToggleHandler(binding.buttonMale, binding.buttonFemale)

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            updateSignUpButton(isEnabled)
        })

        binding.buttonSignUp.setOnClickListener {
            val user = UserRegisterModel(
                userName = binding.editTextLogin.text.toString(),
                name = binding.editTextName.text.toString(),
                password = binding.editTextPassword.text.toString(),
                email = binding.editTextEmail.text.toString(),
                birthDate = binding.editTextDateOfBirth.text.toString(),
                gender = if (binding.buttonMale.isChecked) Gender.MALE else Gender.FEMALE
            )
            viewModel.onSignUpButtonClicked(user)

            if (binding.buttonSignUp.isEnabled) {
                val intent = Intent(requireActivity(), FeedActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun updateViewModelData(
        input: String = "",
        email: Boolean = false,
        name: Boolean = false,
        password: Boolean = false,
        confirmPassword: Boolean = false
    ) {
        viewModel.onSignUpDataChanged(
            binding.editTextLogin.text.toString(),
            if (email)
                input
            else
                binding.editTextEmail.text.toString(),
            if (name)
                input
            else
                binding.editTextName.text.toString(),
            if (password)
                input
            else
                binding.editTextPassword.text.toString(),
            if (confirmPassword)
                input
            else
                binding.editTextConfirmPassword.text.toString(),
            binding.editTextDateOfBirth.text.toString(),
            binding.buttonMale.isChecked,
            binding.buttonFemale.isChecked
        )
    }

    private fun updateSignUpButton(isEnabled: Boolean) {
        EditTextHelper.updateButtonStatus(isEnabled)
        binding.buttonSignUp.isEnabled = isEnabled
        binding.buttonSignUp.setTextColor(
            if (isEnabled)
                resources.getColor(R.color.white)
            else
                resources.getColor(R.color.gray_faded)
        )
        binding.buttonSignUp.setBackgroundResource(
            if (isEnabled)
                R.drawable.button_primary_default
            else
                R.drawable.button_secondary
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
