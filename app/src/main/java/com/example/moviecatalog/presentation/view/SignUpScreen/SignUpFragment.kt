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
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.UserRegisterModel
import com.example.moviecatalog.databinding.FragmentSignUpBinding
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.domain.utils.GenderToggleHandler
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.view.utils.AlertHelper
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel
import com.example.moviecatalog.presentation.viewModel.factory.SignUpViewModelFactory
import com.example.moviecatalog.utils.EditTextHelper

@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: SignUpViewModel
    
    private val alertHelper = AlertHelper()
    private lateinit var genderToggleHandler: GenderToggleHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        initializeViewModel()
        setupTextWatchers()
        setupDateOfBirthPicker()
        setupGenderToggleHandler()
        setupSignUpButton()

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            updateSignUpButton(isEnabled)
        })

        return binding.root
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SignUpViewModelFactory(requireContext())
        )[SignUpViewModel::class.java]
    }

    private fun setupTextWatchers() {
        setupEditText(binding.editTextLogin, UpdateType.LOGIN)
        setupEditText(binding.editTextEmail, UpdateType.EMAIL)
        setupEditText(binding.editTextName, UpdateType.NAME)
        setupEditText(binding.editTextPassword, UpdateType.PASSWORD)
        setupEditText(binding.editTextConfirmPassword, UpdateType.CONFIRM_PASSWORD)
    }

    private fun setupEditText(editText: EditText, updateType: UpdateType) {
        EditTextHelper.hideIcon(editText)
        val passwordIcon =
            if (editText.transformationMethod == PasswordTransformationMethod.getInstance())
            R.drawable.ic_show_password
        else
            R.drawable.ic_hide_password

        editText.addTextChangedListener(
            EditTextHelper.createTextWatcher(editText, passwordIcon) { input ->
                updateViewModelData(input, updateType)
            }
        )

        when (updateType) {
            UpdateType.PASSWORD, UpdateType.CONFIRM_PASSWORD -> {
                EditTextHelper.setHidePasswordOnIconTouch(editText)
            }
            else -> {
                EditTextHelper.setClearTextOnIconTouch(editText)
            }
        }
    }

    private fun setupDateOfBirthPicker() {
        val dateHelper = DateHelper()
        binding.editTextDateOfBirth.setOnClickListener {
            dateHelper.showDatePickerDialog(requireContext(), binding.editTextDateOfBirth)
        }
        binding.editTextDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateViewModelData(binding.editTextDateOfBirth.text.toString(), UpdateType.DOB)
            }

            override fun afterTextChanged(s: Editable?) {
                val dateText = s.toString().trim()
                dateHelper.updateDateDrawable(binding.editTextDateOfBirth, dateText)
            }
        })
    }

    private fun setupGenderToggleHandler() {
        genderToggleHandler = GenderToggleHandler(binding.buttonMale, binding.buttonFemale)
    }

    private fun setupSignUpButton() {
        binding.buttonSignUp.setOnClickListener {
            val user = UserRegisterModel(
                userName = binding.editTextLogin.text.toString(),
                name = binding.editTextName.text.toString(),
                password = binding.editTextPassword.text.toString(),
                email = binding.editTextEmail.text.toString(),
                birthDate = DateHelper().convertToDateTimezones(binding.editTextDateOfBirth.text.toString()),
                gender = if (binding.buttonMale.isChecked) 0 else 1
            )

            viewModel.onSignUpButtonClicked(user, {
                val intent = Intent(requireActivity(), FeedActivity::class.java)
                startActivity(intent)
            }, {
                alertHelper.showAlert(
                    requireContext(),
                    "Ошибка",
                    "Неверные логин или пароль"
                )
            })

            if (binding.buttonSignUp.isEnabled) {
                val intent = Intent(requireActivity(), FeedActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private enum class UpdateType {
        LOGIN,
        EMAIL,
        NAME,
        PASSWORD,
        CONFIRM_PASSWORD,
        DOB
    }

    private fun updateViewModelData(
        input: String = "",
        updateType: UpdateType? = null
    ) {
        val login = binding.editTextLogin.text.toString()
        val email = binding.editTextEmail.text.toString()
        val name = binding.editTextName.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        when (updateType) {
            UpdateType.LOGIN -> viewModel.onSignUpDataChanged(
                input,
                email,
                name,
                password,
                confirmPassword,
                binding.editTextDateOfBirth.text.toString(),
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            UpdateType.EMAIL -> viewModel.onSignUpDataChanged(
                login,
                input,
                name,
                password,
                confirmPassword,
                binding.editTextDateOfBirth.text.toString(),
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            UpdateType.NAME -> viewModel.onSignUpDataChanged(
                login,
                email,
                input,
                password,
                confirmPassword,
                binding.editTextDateOfBirth.text.toString(),
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            UpdateType.PASSWORD -> viewModel.onSignUpDataChanged(
                login,
                email,
                name,
                input,
                confirmPassword,
                binding.editTextDateOfBirth.text.toString(),
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            UpdateType.CONFIRM_PASSWORD -> viewModel.onSignUpDataChanged(
                login,
                email,
                name,
                password,
                input,
                binding.editTextDateOfBirth.text.toString(),
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            UpdateType.DOB -> viewModel.onSignUpDataChanged(
                login,
                email,
                name,
                password,
                confirmPassword,
                input,
                binding.buttonMale.isChecked,
                binding.buttonFemale.isChecked)

            null -> TODO()
        }
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
