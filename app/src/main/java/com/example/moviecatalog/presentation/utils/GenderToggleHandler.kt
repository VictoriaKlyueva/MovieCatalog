package com.example.moviecatalog.presentation.utils

import android.widget.ToggleButton

class GenderToggleHandler(
    private val buttonMale: ToggleButton,
    private val buttonFemale: ToggleButton
) {

    init {
        setupToggleButtons()
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
