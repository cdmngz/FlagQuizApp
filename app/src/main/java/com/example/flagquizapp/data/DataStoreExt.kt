package com.example.flagquizapp.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.quizDataStore by preferencesDataStore(name = "quiz_prefs")
val Context.settingsDataStore by preferencesDataStore(name = "settings")
