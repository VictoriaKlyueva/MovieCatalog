package com.example.moviecatalog.presentation.utils

object ValidationUtils {

    private fun isStrongPassword(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    fun isSignInDataValid(login: String, password: String): Boolean {
        return !(login.isEmpty() || password.isEmpty() ) && isStrongPassword(password)
    }
}