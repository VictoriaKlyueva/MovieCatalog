package com.example.moviecatalog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Переход к основному Activity без задержки
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Закрываем SplashActivity
        }, 0) // Задержка равна 0, переход произойдет немедленно
    }
}
