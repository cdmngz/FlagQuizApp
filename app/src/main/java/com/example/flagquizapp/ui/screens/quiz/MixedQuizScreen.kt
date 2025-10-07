package com.example.flagquizapp.ui.screens.quiz

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.flags.FlagQuizContent
import com.example.flagquizapp.ui.components.quiz.maps.MapQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlin.random.Random

@Composable
fun MixedQuizScreen(
    countries: List<Country>,
    navController: NavHostController
) {
    val context = LocalContext.current

    // 1) Load GeoJSON once
    val geoJson = remember {
        runCatching {
            context.assets.open("maps/countries.geojson")
                .bufferedReader().use { it.readText() }
        }
            .onFailure { Log.e("MixedQuizScreen", "couldn't load geoJson", it) }
            .getOrDefault("{}")
    }

    // 2) Quiz state & timers
    val quizState = rememberQuizState(countries = countries) { }
    var startTimeMs by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var answeredCount by rememberSaveable { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable { mutableStateOf<Country?>(null) }

    // 3) Pick fresh extraInfo each round
    val extraInfo by remember(quizState.currentRound) {
        mutableStateOf(
            quizState.currentRound?.correct?.let { country ->
                if (Random.nextBoolean())
                    "Population: %,d".format(country.population)
                else
                    "Capital: ${country.capital}"
            } ?: ""
        )
    }

    // 4) On answer: bump counter and advance
    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) {
            answeredCount++
            quizState.advance()
        }
    }

    // 5) If finished → score screen
    if (quizState.finished) {
        val endTimeMs = System.currentTimeMillis()
        ScoreScreen(
            score = quizState.score,
            total = quizState.totalRounds,
            durationMs = endTimeMs - startTimeMs,
            completionTimeMs = endTimeMs,
            onRestart = {
                quizState.restart()
                startTimeMs = System.currentTimeMillis()
                answeredCount = 0
            },
            onGoBack = { navController.popBackStack() }
        )
        return
    }

    // 6) Main mixed‐quiz UI
    Box(Modifier.fillMaxSize()) {
        // Close button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close quiz")
        }

        // Content below the close
        quizState.currentRound?.let { round ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
            ) {
                // ── Progress row ──
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Guessed: ${quizState.score}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "${answeredCount + 1}/${quizState.totalRounds}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(8.dp))

                // ── Alternate between flag & map rounds ──
                val isFlagRound = quizState.round % 2 == 0
                if (isFlagRound) {
                    FlagQuizContent(
                        correct = round.correct,
                        options = round.options,
                        answered = quizState.answered,
                        selected = selectedOption,           // ← pass it down
                        extraInfo = extraInfo,
                        onOptionClick = { country ->
                            selectedOption = country                // ← record the tap
                            quizState.onAnswerSelected(country)
                        }
                    )
                } else {
                    MapQuizContent(
                        geoJson = geoJson,
                        correct = round.correct,
                        options = round.options,
                        answered = quizState.answered,
                        extraInfo = extraInfo,
                        onOptionClick = { quizState.onAnswerSelected(it) }
                    )
                }
            }
        }
    }
}
