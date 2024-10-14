package com.example.moviecatalog.presentation.utils

import android.widget.ToggleButton

class GenderToggleHandler(
    private val buttonMale: ToggleButton,
    private val buttonFemale: ToggleButton,
    private val onGenderSelected: (gender: String) -> Unit
) {

    init {
        setupToggleButtons()
    }

    private fun setupToggleButtons() {
        buttonMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonFemale.isChecked = false
                onGenderSelected("Male")
            } else {
                onGenderSelected("")
            }
        }

        buttonFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonMale.isChecked = false
                onGenderSelected("Female")
            } else {
                onGenderSelected("")
            }
        }
    }
}
