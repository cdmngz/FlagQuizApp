package com.example.flagquizapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flagquizapp.ui.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ThemeStore {
    private val THEME = stringPreferencesKey("theme")

    fun themeFlow(context: Context): Flow<ThemeOption> =
        context.settingsDataStore.data.map { prefs ->
            prefs[THEME]?.let { ThemeOption.valueOf(it) } ?: ThemeOption.SYSTEM
        }

    suspend fun setTheme(context: Context, option: ThemeOption) {
        context.settingsDataStore.edit { it[THEME] = option.name }
    }
}
