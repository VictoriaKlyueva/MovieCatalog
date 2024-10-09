package com.example.moviecatalog

import com.example.moviecatalog.R
import android.os.Bundle
import android.view.View;
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_welcome)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            // Логика перехода к экрану входа (или просто печатаем сообщение)
            // startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        }

        registerButton.setOnClickListener {
            // Логика регистрации
            // Здесь вы можете добавить свою логику регистрации
        }
    }
}