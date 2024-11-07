package com.example.moviecatalog.domain.utils

object ValidationUtils {

    private fun isStrongPassword(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[\\w-.]+@[\\w-]+\\.[a-z]{2,6}$".toRegex()
        return emailRegex.matches(email)
    }

    private val months = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )

    fun isDateValid(date: String): Boolean {
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
            if (monthIndex == -1)
                return false

            val daysInMonth = when (monthIndex) {
                0, 2, 4, 6, 7, 9, 11 -> 31
                3, 5, 8, 10 -> 30
                1 -> if (isLeapYear(year)) 29 else 28
                else -> return false
            }

            return day <= daysInMonth
        } catch (e: Exception) {
            false
        }
    }

    fun isSignInDataValid(login: String, password: String): Boolean {
        return !(login.isEmpty() || password.isEmpty() ) && isStrongPassword(password)
    }

    fun isSignUpDataValid(
        login: String,
        email: String,
        name: String,
        password: String,
        confirmPassword: String,
        dateOfBirth: String,
        isMale: Boolean,
        isFemale: Boolean
    ): Boolean {
        val isNull =
            (login.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty() ||
                    confirmPassword.isEmpty() || dateOfBirth.isEmpty() || (!isMale && !isFemale))

        val isEmailValid = isEmailValid(email)
        val isStrongPassword = isStrongPassword(password)
        val isValidPasswords = password == confirmPassword
        val isValidDate = isDateValid(dateOfBirth)

        return !isNull && isEmailValid && isStrongPassword && isValidPasswords && isValidDate
    }
}