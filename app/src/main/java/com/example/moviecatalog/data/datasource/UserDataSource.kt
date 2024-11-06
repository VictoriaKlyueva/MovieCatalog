package com.example.moviecatalog.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviecatalog.common.Constants.GENRES
import com.example.moviecatalog.common.Constants.USER_DATASTORE
import com.example.moviecatalog.common.Constants.USER_ID
import com.example.moviecatalog.data.model.main.GenreModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(USER_DATASTORE)

class UserDataSource(private val context: Context) {

    private object PreferencesKeys {
        val USER_ID_KEY = stringPreferencesKey(USER_ID)
        val GENRES_KEY = stringPreferencesKey(GENRES)
    }

    val userId: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_ID_KEY] }

    private val gson = Gson()

    suspend fun save(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID_KEY] = userId
        }
    }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveGenres(genres: List<GenreModel>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.GENRES_KEY] = gson.toJson(genres)
        }
    }

    val genres: Flow<List<GenreModel>> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.GENRES_KEY]?.let { json ->
                val type = object : TypeToken<List<GenreModel>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } ?: emptyList()
        }

    private suspend fun getCurrentGenres(preferences: Preferences): MutableList<GenreModel> {
        return preferences[PreferencesKeys.GENRES_KEY]?.let { json ->
            val type = object : TypeToken<MutableList<GenreModel>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()
        } ?: mutableListOf()
    }

    suspend fun addGenre(genre: GenreModel) {
        context.dataStore.edit { preferences ->
            val currentGenres = getCurrentGenres(preferences)

            if (!currentGenres.contains(genre)) {
                currentGenres.add(genre)
                preferences[PreferencesKeys.GENRES_KEY] = gson.toJson(currentGenres)
            }
        }
    }

    suspend fun removeGenre(genre: GenreModel) {
        context.dataStore.edit { preferences ->
            val currentGenres = getCurrentGenres(preferences)
            currentGenres.remove(genre)
            preferences[PreferencesKeys.GENRES_KEY] = gson.toJson(currentGenres)
        }
    }

    suspend fun deleteGenres() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.GENRES_KEY)
        }
    }
}
