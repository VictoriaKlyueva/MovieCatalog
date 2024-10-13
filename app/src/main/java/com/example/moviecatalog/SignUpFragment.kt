package com.example.moviecatalog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
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


    private fun getTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0)
                }
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
                    // Проверяем, что нажатие произошло на области иконки
                    if (event.rawX >= (editText.right - editText.compoundDrawables[2].bounds.width())) {
                        // Очищаем текст в EditText
                        editText.setText("")
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
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        editTextLogin = view.findViewById(R.id.editTextLogin)
        hideIcon(editTextLogin)
        editTextLogin.addTextChangedListener(getTextWatcher(editTextLogin))
        setClearTextOnIconTouch(editTextLogin)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        hideIcon(editTextEmail)
        editTextEmail.addTextChangedListener(getTextWatcher(editTextEmail))
        setClearTextOnIconTouch(editTextEmail)

        editTextName = view.findViewById(R.id.editTextName)
        hideIcon(editTextName)
        editTextName.addTextChangedListener(getTextWatcher(editTextName))
        setClearTextOnIconTouch(editTextName)

        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)

        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)
        editTextDateOfBirth.setOnClickListener { showDatePickerDialog() }

        buttonMale = view.findViewById(R.id.buttonMale)
        buttonFemale = view.findViewById(R.id.buttonFemale)
        setupToggleButtons()

        return view
    }

    private val months = arrayOf(
        "января", "февраля", "марта", "апреля", "мая",
        "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val date = "$selectedDay ${months[selectedMonth + 1]} $selectedYear"
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
