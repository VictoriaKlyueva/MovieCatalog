package com.example.moviecatalog.presentation.view.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.utils.DateHelper
import com.example.moviecatalog.presentation.utils.GenderToggleHandler
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel
import com.example.moviecatalog.utils.EditTextHelper


class SignUpFragment : Fragment() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var editTextDateOfBirth: EditText
    private lateinit var buttonMale: ToggleButton
    private lateinit var buttonFemale: ToggleButton
    private lateinit var buttonSignUp: Button

    private lateinit var dateHelper: DateHelper
    private lateinit var genderToggleHandler: GenderToggleHandler

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        initEditText(view)
        setupDateOfBirthPicker(view)
        setupGenderToggle(view)
        setupSignUpButton(view)

        return view
    }

    private fun initEditText(view: View) {
        editTextLogin = view.findViewById(R.id.editTextLogin)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextName = view.findViewById(R.id.editTextName)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)

        val editTexts = listOf(
            editTextLogin,
            editTextEmail,
            editTextName,
            editTextPassword,
            editTextConfirmPassword,
            editTextDateOfBirth
        )

        for (editText in editTexts) {
            EditTextHelper.hideIcon(editText)
            editText.addTextChangedListener(
                getTextWatcher(editText) { input ->
                    updateViewModelData()
                }
            )
            EditTextHelper.setClearTextOnIconTouch(editText)
        }

        EditTextHelper.setHidePasswordOnIconTouch(editTextPassword)
        EditTextHelper.setHidePasswordOnIconTouch(editTextConfirmPassword)
    }

    private fun getTextWatcher(
        editText: EditText,
        onInputChanged: (String) -> Unit
    ): TextWatcher {
        return EditTextHelper.createTextWatcher(editText, R.drawable.ic_clear, onInputChanged)
    }

    private fun updateViewModelData() {
        viewModel.onSignUpDataChanged(
            editTextLogin.text.toString(),
            editTextEmail.text.toString(),
            editTextName.text.toString(),
            editTextPassword.text.toString(),
            editTextConfirmPassword.text.toString(),
            editTextDateOfBirth.text.toString(),
            buttonMale.isChecked,
            buttonFemale.isChecked
        )
    }

    private fun setupDateOfBirthPicker(view: View) {
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)
        dateHelper = DateHelper(requireContext(), editTextDateOfBirth)
        editTextDateOfBirth.setOnClickListener { dateHelper.showDatePickerDialog() }

        editTextDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateViewModelData()
            }

            override fun afterTextChanged(s: Editable?) {
                dateHelper.updateDateIcon(editTextDateOfBirth, s.toString())
            }
        })
    }

    private fun setupGenderToggle(view: View) {
        buttonMale = view.findViewById(R.id.buttonMale)
        buttonFemale = view.findViewById(R.id.buttonFemale)
        genderToggleHandler = GenderToggleHandler(buttonMale, buttonFemale) { selectedGender ->
            viewModel.onGenderSelected(selectedGender)
        }
    }

    private fun setupSignUpButton(view: View) {
        buttonSignUp = view.findViewById(R.id.buttonSignUp)

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            EditTextHelper.updateButtonStatus(isEnabled)
            buttonSignUp.isEnabled = isEnabled
            buttonSignUp.setTextColor(
                if (isEnabled)
                    resources.getColor(R.color.white)
                else
                    resources.getColor(R.color.gray_faded)
            )
            buttonSignUp.setBackgroundResource(
                if (isEnabled)
                    R.drawable.button_primary_default
                else
                    R.drawable.button_secondary
            )
        })
    }
}
