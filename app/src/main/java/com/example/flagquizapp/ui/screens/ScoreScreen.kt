package com.example.flagquizapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.flagquizapp.data.quizDataStore
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    // avoid divide–by–zero
    val safeTotal = total.coerceAtLeast(1)
    val safeScore = score.coerceIn(0, safeTotal)
    val progress = safeScore / safeTotal.toFloat()
    val animatedProgress by animateFloatAsState(progress, label = "")

    val durationSec = durationMs / 1000

    val context = LocalContext.current
    val bestScoreFlow = context.quizDataStore.data.map { it[QuizPrefsKeys.BEST_SCORE] ?: 0 }
    val bestTimeFlow =
        context.quizDataStore.data.map { it[QuizPrefsKeys.BEST_TIME_MS] ?: Long.MAX_VALUE }
    val bestTsFlow = context.quizDataStore.data.map { it[QuizPrefsKeys.BEST_TIMESTAMP] ?: 0L }

    val bestScore by bestScoreFlow.collectAsState(initial = 0)
    val bestTime by bestTimeFlow.collectAsState(initial = Long.MAX_VALUE)
    val bestTs by bestTsFlow.collectAsState(initial = 0L)

    val isNewRecord = remember(safeScore, durationMs, bestScore, bestTime) {
        safeScore > bestScore || (safeScore == bestScore && durationMs < bestTime)
    }

    LaunchedEffect(isNewRecord) {
        if (isNewRecord) {
            try {
                context.quizDataStore.edit { prefs ->
                    prefs[QuizPrefsKeys.BEST_SCORE] = safeScore
                    prefs[QuizPrefsKeys.BEST_TIME_MS] = durationMs
                    prefs[QuizPrefsKeys.BEST_TIMESTAMP] = completionTimeMs
                }
            } catch (t: Throwable) {
                // swallow any DataStore errors
                t.printStackTrace()
            }
        }
    }

    val dateFmt = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // 1) Determinate ring
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(120.dp),
                    strokeWidth = 8.dp,
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                )
                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    fontSize = 24.sp
                )
            }

            // 2) Message
            val msg = when {
                safeScore == safeTotal -> "🏆 Perfect!"
                safeScore >= safeTotal / 2 -> "🎉 Nice work!"
                else -> "👍 Keep practicing!"
            }
            Text(msg, style = MaterialTheme.typography.headlineSmall)

            // 3) Raw
            Text("Score: $safeScore / $safeTotal")
            Text("Time: ${durationSec}s")

            // 4) Record info
            if (bestScore > 0) {
                if (isNewRecord) {
                    Text("🎉 New record! $safeScore in ${durationSec}s")
                } else {
                    val bestSec = bestTime / 1000
                    Text(
                        "🔖 Record: $bestScore in ${bestSec}s on ${dateFmt.format(Date(bestTs))}"
                    )
                }
            }

            // 5) Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onRestart, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Refresh, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Again")
                }
                OutlinedButton(onClick = onGoBack, modifier = Modifier.weight(1f)) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Back")
                }
            }
        }
    }
}
