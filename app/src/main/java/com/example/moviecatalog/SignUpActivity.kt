package com.example.moviecatalog

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalog.presentation.view.Activities.WelcomeActivity


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_sign_up)

        val backButton = findViewById<ImageButton>(R.id.back_button)

        backButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    WelcomeActivity::class.java
                )
            )
        }
    }
}