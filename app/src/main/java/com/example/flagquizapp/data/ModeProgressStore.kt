package com.example.flagquizapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map

data class ModeKey(
    val continent: String,      // Continent.name
    val subregion: String?,     // null for "All"
    val modeIndex: Int          // 0..3
)

private fun prefName(key: ModeKey) =
    "progress_${key.continent}_${key.subregion ?: "ALL"}_${key.modeIndex}"

object ModeProgressStore {
    fun flow(context: Context, key: ModeKey) =
        context.quizDataStore.data.map { it[intPreferencesKey(prefName(key))] ?: 0 }

    suspend fun save(context: Context, key: ModeKey, percent: Int) {
        context.quizDataStore.edit {
            it[intPreferencesKey(prefName(key))] = percent.coerceIn(0, 100)
        }
    }
}
