package com.moondroid.legacy.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PREFERENCES_NAME = "BeHealthy_Preferences"
private val Context.datastore by preferencesDataStore(PREFERENCES_NAME)

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.datastore
    private val gson = Gson()

    private fun putInt(key: String, value: Int) = CoroutineScope(Dispatchers.IO).launch {
        dataStore.edit { editor -> editor[intPreferencesKey(key)] = value }
    }

    private suspend fun getInt(key: String, defaultValue: Int): Int {
        return dataStore.data.map { preference ->
            preference[intPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun putString(key: String, value: String) = CoroutineScope(Dispatchers.IO).launch {
        dataStore.edit { editor -> editor[stringPreferencesKey(key)] = value }
    }

    private suspend fun getString(key: String, defaultValue: String = ""): String {
        return dataStore.data.map { preference ->
            preference[stringPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun putBoolean(key: String, value: Boolean) = CoroutineScope(Dispatchers.IO).launch {
        dataStore.edit { editor -> editor[booleanPreferencesKey(key)] = value }
    }

    private suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return dataStore.data.map { preference ->
            preference[booleanPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    suspend fun isTutorial(): Boolean {
        val isTutorial = getBoolean("TUTORIAL", true)
        if (isTutorial) {
            putBoolean("TUTORIAL", false)
        }
        return isTutorial
    }
}