package com.example.moviecatalog.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.datasource.TokenDataSource
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.domain.usecase.LogoutUseCase

class ProfileViewModel(
    context: Context
): ViewModel() {

    private val tokenDataSource = TokenDataSource(context)
    private val authRepository = AuthRepositoryImpl(tokenDataSource)

    private val logoutUseCase = LogoutUseCase(authRepository)

    fun onLogoutButtonClicked(callback: (Boolean) -> Unit) {
        logoutUseCase.execute { success ->
            if (success) {
                println("Успешный выход из аккаунта")
            } else {
                println("Ошибка выхода")
            }
            callback(success)
        }
    }
}
