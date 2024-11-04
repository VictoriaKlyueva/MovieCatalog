package com.example.moviecatalog.presentation.view.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class AlertHelper {
    fun showAlert(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}