package com.example.moviecatalog.presentation.view.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.viewModel.SignInViewModel


class SignInFragment : Fragment() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignIn: Button

    private lateinit var viewModel: SignInViewModel

    private fun createTextWatcher(editText: EditText, icon: Int, isPassword: Boolean = false): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    if (isPassword && editText.transformationMethod is HideReturnsTransformationMethod) {
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    editText.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                } else {
                    editText.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        icon,
                        0
                    )
                }

                viewModel.onLoginDataChanged(editTextLogin.text.toString(), editTextPassword.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setClearTextOnIconTouch(editText: EditText) {
        editText.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[2] != null) {
                    if (event.rawX >=
                        (editText.right - editText.compoundDrawables[2].bounds.width())) {
                        editText.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setHidePasswordOnIconTouch(editText: EditText) {
        editText.setOnTouchListener { v, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[2] != null) {
                    if (event.rawX >= (editText.right - editText.compoundDrawables[2].bounds.width())) {
                        if (editText.transformationMethod is HideReturnsTransformationMethod) {
                            // Скрыть пароль
                            editText.transformationMethod = PasswordTransformationMethod.getInstance()
                            editText.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_show_password,
                                0
                            )
                        } else {
                            // Показать пароль
                            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                            editText.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_hide_password,
                                0
                            )
                        }
                        editText.setSelection(editText.text.length)
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun hideIcon(editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        editTextLogin = view.findViewById(R.id.editTextLogin)
        hideIcon(editTextLogin)
        editTextLogin.addTextChangedListener(createTextWatcher(editTextLogin, R.drawable.ic_clear))
        setClearTextOnIconTouch(editTextLogin)

        editTextPassword = view.findViewById(R.id.editTextPassword)
        hideIcon(editTextPassword)

        val passwordIcon =
            if (editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        editTextPassword.addTextChangedListener(createTextWatcher(
            editTextPassword,
            passwordIcon,
            true
        ))
        setHidePasswordOnIconTouch(editTextPassword)

        buttonSignIn = view.findViewById(R.id.buttonSignIn)

        viewModel.isButtonEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            buttonSignIn.isEnabled = isEnabled
            buttonSignIn.setTextColor(if (isEnabled) resources.getColor(R.color.white) else resources.getColor(R.color.gray_faded))
            buttonSignIn.setBackgroundResource(if (isEnabled) R.drawable.button_primary_default else R.drawable.button_secondary)
        })

        return view
    }
}