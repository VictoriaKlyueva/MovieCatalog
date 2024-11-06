package com.example.moviecatalog.domain.utils

import com.example.moviecatalog.data.interceptor.AuthInterceptor

class CustomAuthFailureHandler : AuthInterceptor.AuthFailureHandler {
    override fun onAuthFailure() {
        println("Типо идем в велкам скрин")
    }
}