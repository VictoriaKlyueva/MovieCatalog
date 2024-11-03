package com.example.moviecatalog.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("user_datastore")

class UserDataSource(private val context: Context) {

    private object PreferencesKeys {
        val USER_ID_KEY = stringPreferencesKey("userId")
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID_KEY] = userId
        }
    }

    fun fetchUserId(): String? {
        val userId: MutableStateFlow<String?> = MutableStateFlow(null)

        runBlocking {
            userId.value = context.dataStore.data
                .map { it[PreferencesKeys.USER_ID_KEY] }
                .firstOrNull()
        }

        return userId.value
    }

    suspend fun deleteUserData()  {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}