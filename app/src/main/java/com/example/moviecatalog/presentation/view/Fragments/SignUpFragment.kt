package com.example.moviecatalog.presentation.view.Fragments

import androidx.lifecycle.ViewModelProvider
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.utils.DateHelper
import com.example.moviecatalog.presentation.utils.ValidationUtils
import com.example.moviecatalog.presentation.viewModel.SignUpViewModel
import com.example.moviecatalog.utils.EditTextHelper
import java.util.Calendar


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

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        editTextLogin = view.findViewById(R.id.editTextLogin)
        EditTextHelper.hideIcon(editTextLogin)
        editTextLogin.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextLogin,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignUpDataChanged(
                input,
                editTextEmail.text.toString(),
                editTextName.text.toString(),
                editTextPassword.text.toString(),
                editTextConfirmPassword.text.toString(),
                editTextDateOfBirth.text.toString(),
                buttonMale.isChecked,
                buttonFemale.isChecked
            )
        })
        EditTextHelper.setClearTextOnIconTouch(editTextLogin)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        EditTextHelper.hideIcon(editTextEmail)
        editTextEmail.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextEmail,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignUpDataChanged(
                editTextLogin.text.toString(),
                input,
                editTextName.text.toString(),
                editTextPassword.text.toString(),
                editTextConfirmPassword.text.toString(),
                editTextDateOfBirth.text.toString(),
                buttonMale.isChecked,
                buttonFemale.isChecked
            )
        })
        EditTextHelper.setClearTextOnIconTouch(editTextEmail)

        editTextName = view.findViewById(R.id.editTextName)
        EditTextHelper.hideIcon(editTextName)
        editTextName.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextName,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignUpDataChanged(
                editTextLogin.text.toString(),
                editTextEmail.text.toString(),
                input,
                editTextPassword.text.toString(),
                editTextConfirmPassword.text.toString(),
                editTextDateOfBirth.text.toString(),
                buttonMale.isChecked,
                buttonFemale.isChecked
            )
        })
        EditTextHelper.setClearTextOnIconTouch(editTextName)

        editTextPassword = view.findViewById(R.id.editTextPassword)
        EditTextHelper.hideIcon(editTextPassword)

        val passwordIcon =
            if (editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        editTextPassword.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextPassword,
            passwordIcon
        ) { input ->
            viewModel.onSignUpDataChanged(
                editTextLogin.text.toString(),
                editTextEmail.text.toString(),
                editTextName.text.toString(),
                input,
                editTextConfirmPassword.text.toString(),
                editTextDateOfBirth.text.toString(),
                buttonMale.isChecked,
                buttonFemale.isChecked
            )
        })
        EditTextHelper.setHidePasswordOnIconTouch(editTextPassword)

        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        EditTextHelper.hideIcon(editTextConfirmPassword)

        val confirmPasswordIcon =
            if (editTextConfirmPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        editTextConfirmPassword.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextConfirmPassword,
            confirmPasswordIcon
        ) { input ->
            viewModel.onSignUpDataChanged(
                editTextLogin.text.toString(),
                editTextEmail.text.toString(),
                editTextName.text.toString(),
                editTextPassword.text.toString(),
                input,
                editTextDateOfBirth.text.toString(),
                buttonMale.isChecked,
                buttonFemale.isChecked
            )
        })
        EditTextHelper.setHidePasswordOnIconTouch(editTextConfirmPassword)

        val dateHelper = DateHelper()
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)
        editTextDateOfBirth.setOnClickListener {
            dateHelper.showDatePickerDialog(requireContext(), editTextDateOfBirth)
        }
        editTextDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

            override fun afterTextChanged(s: Editable?) {
                val dateText = s.toString().trim()

                dateHelper.updateDateDrawable(editTextDateOfBirth, dateText)
            }
        })

        buttonMale = view.findViewById(R.id.buttonMale)
        buttonFemale = view.findViewById(R.id.buttonFemale)
        setupToggleButtons()

        buttonSignUp = view.findViewById(R.id.buttonSignUp)

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            EditTextHelper.updateButtonStatus(isEnabled)
            buttonSignUp.isEnabled = isEnabled
            buttonSignUp.setTextColor(
                if (isEnabled)
                    resources.getColor(R.color.white)
                else resources.getColor(R.color.gray_faded)
            )
            buttonSignUp.setBackgroundResource(
                if (isEnabled)
                    R.drawable.button_primary_default
                else
                    R.drawable.button_secondary
            )
        })

        return view
    }

    private fun setupToggleButtons() {
        buttonMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonFemale.isChecked = false
            }
        }

        buttonFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonMale.isChecked = false
            }
        }
    }
}
