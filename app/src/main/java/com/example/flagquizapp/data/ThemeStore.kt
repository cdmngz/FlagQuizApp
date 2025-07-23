// com/example/flagquizapp/data/ThemeStore.kt
package com.example.flagquizapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.flagquizapp.ui.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object ThemeStore {
    private val THEME = stringPreferencesKey("theme")

    fun themeFlow(context: Context): Flow<ThemeOption> =
        context.dataStore.data.map { prefs ->
            prefs[THEME]?.let { ThemeOption.valueOf(it) } ?: ThemeOption.SYSTEM
        }

    suspend fun setTheme(context: Context, option: ThemeOption) {
        context.dataStore.edit { it[THEME] = option.name }
    }
}
