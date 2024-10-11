package com.example.moviecatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            println("о ебать запустилось")

            setContentView(R.layout.activity_sign_in)
    }
}