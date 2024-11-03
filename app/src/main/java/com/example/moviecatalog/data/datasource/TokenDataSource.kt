package com.example.moviecatalog.data.datasource

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviecatalog.common.Constants

class TokenDataSource(context: Context) {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "token_preferences_key",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getToken(): String? {
        return sharedPreferences.getString("user_token_key", null)
    }

    fun save(token: String) {
        sharedPreferences.edit()
            .putString("user_token_key", token)
            .apply()
    }

    fun delete() {
        sharedPreferences.edit()
            .remove("user_token_key")
            .apply()
    }
}