package com.example.moviecatalog.data.datasource

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviecatalog.common.Constants
import com.example.moviecatalog.common.Constants.TOKEN_PREFERENCE_KEY
import com.example.moviecatalog.common.Constants.USER_TOKEN_KEY

class TokenDataSource(context: Context) {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        TOKEN_PREFERENCE_KEY,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getToken(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    fun save(token: String) {
        sharedPreferences.edit()
            .putString(USER_TOKEN_KEY, token)
            .apply()
    }

    fun delete() {
        sharedPreferences.edit()
            .remove(USER_TOKEN_KEY)
            .apply()
    }
}