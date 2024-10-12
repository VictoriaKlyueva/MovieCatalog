package com.example.moviecatalog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
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


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        editTextLogin = view.findViewById(R.id.editTextLogin)
        editTextLogin.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editTextLogin.compoundDrawables[2] != null) {
                    if (event.rawX >= (editTextLogin.right - editTextLogin.compoundDrawables[2].bounds.width())) {
                        editTextLogin.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextEmail.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editTextEmail.compoundDrawables[2] != null) {
                    if (event.rawX >= (editTextEmail.right - editTextEmail.compoundDrawables[2].bounds.width())) {
                        editTextEmail.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        editTextName = view.findViewById(R.id.editTextName)
        editTextName.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (editTextName.compoundDrawables[2] != null) {
                    if (event.rawX >= (editTextName.right - editTextName.compoundDrawables[2].bounds.width())) {
                        editTextName.setText("")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

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
                buttonFemale.setBackgroundResource(R.drawable.toggle_inactive)
                buttonMale.setBackgroundResource(R.drawable.toggle_active)
            }
        }

        buttonFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonMale.isChecked = false
                buttonMale.setBackgroundResource(R.drawable.toggle_inactive)
                buttonFemale.setBackgroundResource(R.drawable.toggle_active)
            }
        }
    }
}
