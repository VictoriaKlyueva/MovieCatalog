package com.example.moviecatalog.domain.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import com.example.moviecatalog.R
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatterBuilder
import java.util.Calendar
import java.util.Locale

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

    fun convertDate(inputDate: String): String {
        val formatter = DateTimeFormatterBuilder()
            .parseDefaulting(java.time.temporal.ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(java.time.temporal.ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(java.time.temporal.ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(java.time.temporal.ChronoField.NANO_OF_SECOND, 0)
            .appendPattern("d MMMM yyyy")
            .toFormatter(Locale("ru"))

        val localDate = LocalDate.parse(inputDate, formatter)

        return localDate.atStartOfDay(ZoneOffset.UTC)
            .toString()
    }

    fun minutesToHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return "$hours ч $remainingMinutes мин"
    }

}
