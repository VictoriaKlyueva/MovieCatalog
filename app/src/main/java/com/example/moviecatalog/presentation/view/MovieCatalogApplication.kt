package com.example.moviecatalog.presentation.view

import android.app.Application
import com.example.moviecatalog.data.api.client.ApiClient
import com.example.moviecatalog.presentation.utils.CustomAuthFailureHandler

class MovieCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val authFailureHandler = CustomAuthFailureHandler()
        ApiClient.init(this, authFailureHandler)
    }
}
