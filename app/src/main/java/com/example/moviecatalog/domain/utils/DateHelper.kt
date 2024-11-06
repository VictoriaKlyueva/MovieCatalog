package com.example.moviecatalog.domain.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.moviecatalog.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDateTimezones(inputDate: String): String {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertFromDateTimezones(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(input, inputFormatter)

        val outputFormatter = DateTimeFormatterBuilder()
            .appendPattern("d MMMM yyyy")
            .toFormatter(Locale("ru"))

        return dateTime.format(outputFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertFromDateTimezonesSeconds(input: String): String {
        println(input)

        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
        val dateTime = LocalDateTime.parse(input, inputFormatter)

        val outputFormatter = DateTimeFormatterBuilder()
            .appendPattern("d MMMM yyyy")
            .toFormatter(Locale("ru"))

        return dateTime.format(outputFormatter)
    }

    fun getCurrentTime(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }
}
