package com.example.moviecatalog.presentation.utils

import com.example.moviecatalog.data.interceptor.AuthInterceptor

class CustomAuthFailureHandler : AuthInterceptor.AuthFailureHandler {

    override fun onAuthFailure() { }
}