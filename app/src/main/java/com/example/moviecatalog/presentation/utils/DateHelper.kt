package com.example.moviecatalog.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import com.example.moviecatalog.R
import java.util.Calendar

class DateHelper {
    fun updateDateDrawable(editText: EditText, dateText: String) {
        if (dateText.isEmpty() || !ValidationUtils.isDateValid(dateText)) {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_calendar,
                0
            )
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_calendar_active,
                0
            )
        }
    }

    private val months = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )

    fun showDatePickerDialog(context: Context, editTextDateOfBirth: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val date = "$selectedDay ${months[selectedMonth]} $selectedYear"
                editTextDateOfBirth.setText(date)
            }, year, month, day
        )
        datePickerDialog.show()
    }
}
