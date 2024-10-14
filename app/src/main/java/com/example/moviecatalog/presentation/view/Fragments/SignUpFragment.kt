package com.example.moviecatalog.presentation.view.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.example.moviecatalog.R
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

    private fun isValidData(): Boolean {
        val isNull = (editTextLogin.text.toString().isEmpty() ||
                editTextEmail.text.toString().isEmpty() ||
                editTextName.text.toString().isEmpty() ||
                editTextPassword.text.toString().isEmpty() ||
                editTextConfirmPassword.text.toString().isEmpty() ||
                editTextDateOfBirth.text.toString().isEmpty() ||
                (!buttonMale.isChecked && !buttonFemale.isChecked)
                )

        val isValidEmail = isValidEmail(editTextEmail.text.toString())
        val isStrongPassword = isStrongPassword(editTextPassword.text.toString())
        val isValidPasswords =
            editTextPassword.text.toString() == editTextConfirmPassword.text.toString()
        val isValidDate = isValidDate(editTextDateOfBirth.text.toString())

        return !isNull && isValidEmail && isStrongPassword && isValidPasswords && isValidDate
    }

    private fun isStrongPassword(password: String): Boolean {
        if (password.length < 8)
            return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[\\w-.]+@[\\w-]+\\.[a-z]{2,6}$".toRegex()
        return emailRegex.matches(email)
    }

    private fun getTextWatcher(editText: EditText, icon: Int, isPassword: Boolean = false): TextWatcher {
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
                updateButtonState()
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        editTextLogin = view.findViewById(R.id.editTextLogin)
        hideIcon(editTextLogin)
        editTextLogin.addTextChangedListener(getTextWatcher(editTextLogin, R.drawable.ic_clear))
        setClearTextOnIconTouch(editTextLogin)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        hideIcon(editTextEmail)
        editTextEmail.addTextChangedListener(getTextWatcher(editTextEmail, R.drawable.ic_clear))
        setClearTextOnIconTouch(editTextEmail)

        editTextName = view.findViewById(R.id.editTextName)
        hideIcon(editTextName)
        editTextName.addTextChangedListener(getTextWatcher(editTextName, R.drawable.ic_clear))
        setClearTextOnIconTouch(editTextName)

        editTextPassword = view.findViewById(R.id.editTextPassword)
        hideIcon(editTextPassword)

        val passwordIcon =
            if (editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        editTextPassword.addTextChangedListener(getTextWatcher(
            editTextPassword,
            passwordIcon,
            true
        ))
        setHidePasswordOnIconTouch(editTextPassword)

        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        hideIcon(editTextConfirmPassword)

        val confirmPasswordIcon =
            if (editTextConfirmPassword.transformationMethod == PasswordTransformationMethod.getInstance())
                R.drawable.ic_show_password
            else
                R.drawable.ic_hide_password

        editTextConfirmPassword.addTextChangedListener(getTextWatcher(
            editTextConfirmPassword,
            confirmPasswordIcon,
            true
        ))
        setHidePasswordOnIconTouch(editTextConfirmPassword)

        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)
        editTextDateOfBirth.setOnClickListener { showDatePickerDialog() }
        editTextDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {
                val dateText = s.toString().trim()

                if (dateText.isEmpty() || !isValidDate(dateText)) {
                    editTextDateOfBirth.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_calendar,
                        0
                    )
                } else {
                    editTextDateOfBirth.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_calendar_active,
                        0
                    )
                }
            }
        })

        buttonMale = view.findViewById(R.id.buttonMale)
        buttonFemale = view.findViewById(R.id.buttonFemale)
        setupToggleButtons()

        buttonSignUp = view.findViewById(R.id.buttonSignUp)

        return view
    }

    private fun updateButtonState() {
        println("Вызвана updateButtonState")
        println(isValidData())
        if (isValidData()) {
            buttonSignUp.isEnabled = true
            buttonSignUp.setTextColor(resources.getColor(R.color.white))
            buttonSignUp.setBackgroundResource(R.drawable.button_primary_default)
        } else {
            buttonSignUp.isEnabled = false
            buttonSignUp.setTextColor(resources.getColor(R.color.gray_faded))
            buttonSignUp.setBackgroundResource(R.drawable.button_secondary)
        }
    }

    private fun isValidDate(date: String): Boolean {
        // Является ли год високосным
        fun isLeapYear(year: Int): Boolean {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        }

        return try {
            val (dayStr, monthStr, yearStr) = date.split(" ").map { it.trim() }

            val day = dayStr.toInt()
            val year = yearStr.toInt()

            if (year < 1900 || year > 2020) return false

            if (day < 1 || day > 31) return false

            val monthIndex = months.indexOf(monthStr.toLowerCase())
            if (monthIndex == -1) return false // Неверный месяц

            val daysInMonth = when (monthIndex) {
                // Январь, март, май, июль, август, сентябрь и декабрь
                0, 2, 4, 6, 7, 9, 11 -> 31
                // Апрель, июнь, сентябрь, ноябрь
                3, 5, 8, 10 -> 30
                // Февраль
                1 -> if (isLeapYear(year)) 29 else 28
                else -> return false
            }

            return day <= daysInMonth
        } catch (e: Exception) {
            false
        }
    }

    private val months = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val date = "$selectedDay ${months[selectedMonth]} $selectedYear"
                editTextDateOfBirth.setText(date)
            }, year, month, day
        )
        datePickerDialog.show()
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
