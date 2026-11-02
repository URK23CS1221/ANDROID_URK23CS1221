package com.example.ex06

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {
    private val darkModeKey = booleanPreferencesKey("dark_mode")

    val darkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: false
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[darkModeKey] = enabled
        }
    }
}
