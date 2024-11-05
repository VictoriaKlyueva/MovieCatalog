package com.example.moviecatalog.presentation.view

import android.app.Application
import com.example.moviecatalog.data.api.client.ApiClient

class MovieCatalogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.init(this)
    }
}
