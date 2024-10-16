package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.viewModel.SignInViewModel
import com.example.moviecatalog.utils.EditTextHelper


class SignInFragment : Fragment() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignIn: Button

    private lateinit var viewModel: SignInViewModel

    private fun editTextLoginProcessing(view: View) {
        editTextLogin = view.findViewById(R.id.editTextLogin)
        EditTextHelper.hideIcon(editTextLogin)
        editTextLogin.addTextChangedListener(EditTextHelper.createTextWatcher(
            editTextLogin,
            R.drawable.ic_clear
        ) { input ->
            viewModel.onSignInDataChanged(input, editTextPassword.text.toString())
        })
        EditTextHelper.setClearTextOnIconTouch(editTextLogin)
    }

    private fun editTextPasswordProcessing(view: View) {
        editTextPassword = view.findViewById(R.id.editTextPassword)
        EditTextHelper.hideIcon(editTextPassword)

        val passwordIcon =
            if (editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
            R.drawable.ic_show_password
        else
            R.drawable.ic_hide_password

        editTextPassword.addTextChangedListener(
            EditTextHelper.createTextWatcher(editTextPassword, passwordIcon) { input ->
            viewModel.onSignInDataChanged(editTextLogin.text.toString(), input)
        })
        EditTextHelper.setHidePasswordOnIconTouch(editTextPassword)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        editTextLoginProcessing(view)
        editTextPasswordProcessing(view)

        buttonSignIn = view.findViewById(R.id.buttonSignIn)

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            EditTextHelper.updateButtonStatus(isEnabled)
            buttonSignIn.isEnabled = isEnabled
            buttonSignIn.setTextColor(
                if (isEnabled)
                    resources.getColor(R.color.white)
                else resources.getColor(R.color.gray_faded)
            )
            buttonSignIn.setBackgroundResource(
                if (isEnabled)
                    R.drawable.button_primary_default
                else
                    R.drawable.button_secondary
            )
        })

        return view
    }
}