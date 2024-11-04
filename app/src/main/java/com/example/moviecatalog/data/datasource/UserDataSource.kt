package com.example.moviecatalog.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviecatalog.common.Constants.USER_DATASTORE
import com.example.moviecatalog.common.Constants.USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(USER_DATASTORE)

class UserDataSource(private val context: Context) {

    private object PreferencesKeys {
        val USER_ID_KEY = stringPreferencesKey(USER_ID)
    }

    suspend fun save(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID_KEY] = userId
        }
    }

    val userId: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_ID_KEY] }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}