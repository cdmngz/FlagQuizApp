package com.example.flagquizapp.ui.screens

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "quiz_prefs")

private object QuizPrefsKeys {
    val BEST_SCORE = intPreferencesKey("best_score")
    val BEST_TIME_MS = longPreferencesKey("best_time_ms")
    val BEST_TIMESTAMP = longPreferencesKey("best_time_timestamp")
}

@Composable
fun ScoreScreen(
    score: Int,
    total: Int,
    durationMs: Long,
    completionTimeMs: Long,
    onRestart: () -> Unit,
    onGoBack: () -> Unit
) {
    val safeScore = score.coerceIn(0, total.coerceAtLeast(1))
    val progress = safeScore / total.toFloat()
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "")

    val context = LocalContext.current

    // --- load stored record ---
    val bestScoreFlow = context.dataStore.data.map { prefs ->
        prefs[QuizPrefsKeys.BEST_SCORE] ?: 0
    }
    val bestTimeFlow = context.dataStore.data.map { prefs ->
        prefs[QuizPrefsKeys.BEST_TIME_MS] ?: Long.MAX_VALUE
    }
    val bestTsFlow = context.dataStore.data.map { prefs ->
        prefs[QuizPrefsKeys.BEST_TIMESTAMP] ?: 0L
    }

    val bestScore by bestScoreFlow.collectAsState(initial = 0)
    val bestTime by bestTimeFlow.collectAsState(initial = Long.MAX_VALUE)
    val bestTs   by bestTsFlow.collectAsState(initial = 0L)

    // --- decide if this run is a new record ---
    val isNewRecord = remember(score, durationMs, bestScore, bestTime) {
        score > bestScore || (score == bestScore && durationMs < bestTime)
    }

    // --- persist if new record ---
    LaunchedEffect(isNewRecord) {
        if (isNewRecord) {
            context.dataStore.edit { prefs ->
                prefs[QuizPrefsKeys.BEST_SCORE]      = score
                prefs[QuizPrefsKeys.BEST_TIME_MS]    = durationMs
                prefs[QuizPrefsKeys.BEST_TIMESTAMP]  = completionTimeMs
            }
        }
    }

    // --- formatters ---
    val dateFmt     = SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault())
    val durationSec = durationMs / 1000

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                // 1) Progress ring
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.size(120.dp),
                        strokeWidth = 8.dp,
                        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                    )
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.height(24.dp))

                // 2) Message
                val message = when {
                    safeScore == total -> "🏆 Perfect!"
                    safeScore >= total / 2 -> "🎉 Nice work!"
                    else -> "👍 Keep practicing!"
                }
                Text(message, style = MaterialTheme.typography.headlineSmall)

                Spacer(Modifier.height(8.dp))

                // 3) Raw score
                Text("Score: $safeScore / $total", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(16.dp))

                // 4) Timestamp & duration
                Text("Your time: ${durationSec}s",
                    style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(16.dp))

                // 5) Record display
                if (bestScore > 0) {
                    if (isNewRecord) {
                        Text(
                            "🎉 New record! $score correct in ${durationSec}s",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        val bestSec = bestTime / 1000
                        Text(
                            "🔖 Record: $bestScore correct in ${bestSec}s on ${dateFmt.format(Date(bestTs))}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 6) Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onRestart, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Again")
                    }
                    OutlinedButton(onClick = onGoBack, modifier = Modifier.weight(1f)) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Go back")
                        Spacer(Modifier.width(8.dp))
                        Text("Back")
                    }
                }
            }
    }
}
