package com.example.moviecatalog.utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalog.R

object EditTextHelper {

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    fun createTextWatcher(
        editText: EditText,
        @DrawableRes clearIcon: Int,
        onTextChanged: (String) -> Unit
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
                }

                // Вызываем функцию для обработки введенных данных
                onTextChanged(editText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setClearTextOnIconTouch(editText: EditText) {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[2] != null) {
                    if (event.rawX >= (editText.right - editText.compoundDrawables[2].bounds.width())) {
                        editText.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setHidePasswordOnIconTouch(editText: EditText) {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editText.compoundDrawables[2] != null) {
                    if (event.rawX >= (editText.right - editText.compoundDrawables[2].bounds.width())) {
                        if (editText.transformationMethod is HideReturnsTransformationMethod) {
                            editText.transformationMethod = PasswordTransformationMethod.getInstance()
                            editText.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_show_password,
                                0
                            )
                        } else {
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

    fun hideIcon(editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    fun updateButtonStatus(isEnabled: Boolean) {
        _isButtonEnabled.value = isEnabled
    }
}
