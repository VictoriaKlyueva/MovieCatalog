package com.example.moviecatalog.data.interceptor

import com.example.moviecatalog.common.Constants.AUTHORIZATION_HEADER
import com.example.moviecatalog.common.Constants.BEARER
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.domain.utils.NavigationManager
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val tokenDataSource: TokenDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        tokenDataSource.getToken()?.let { token ->
            builder.addHeader(AUTHORIZATION_HEADER, "$BEARER $token")
        }

        val response = chain.proceed(builder.build())

        if (response.code == 401) {
            handleUnauthorized()
        }

        return response
    }

    private fun handleUnauthorized() {
        NavigationManager.notifyUserToGoToWelcomeScreen()
    }
}
