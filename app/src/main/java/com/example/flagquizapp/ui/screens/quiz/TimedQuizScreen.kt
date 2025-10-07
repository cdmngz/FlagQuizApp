package com.example.flagquizapp.ui.screens.quiz

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.flags.FlagQuizContent
import com.example.flagquizapp.ui.components.quiz.maps.MapQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TimedQuizScreen(
    countries: List<Country>,
    navController: NavHostController
) {
    val context = LocalContext.current

    // 1) load GeoJSON once
    val geoJson = remember {
        runCatching {
            context.assets.open("maps/countries.geojson")
                .bufferedReader()
                .use { it.readText() }
        }.onFailure {
            Log.e("TimedQuizScreen", "Couldn't load countries.geojson", it)
        }.getOrDefault("{}")
    }

    // quiz state
    val quizState = rememberQuizState(
        countries = countries,
        onQuizFinished = { /* handled via .finished */ }
    )

    // timers + counters
    var timeLeft by rememberSaveable { mutableIntStateOf(10) }
    var startTimeMs by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var answeredCount by rememberSaveable { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable { mutableStateOf<Country?>(null) }

    // Reset timer on new round
    LaunchedEffect(quizState.round) {
        timeLeft = 10
        while (timeLeft > 0 && !quizState.answered) {
            delay(1000)
            timeLeft--
        }
        // if time runs out, auto-answer wrong
        if (!quizState.answered) {
            quizState.currentRound?.let { current ->
                val wrong = current.options.firstOrNull { it != current.correct }
                    ?: current.correct
                quizState.onAnswerSelected(wrong)
            }
        }
    }

    // Advance on answer and bump guessed‐count
    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) {
            answeredCount++
            quizState.advance()
        }
    }

    // End‐of‐quiz
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
                timeLeft = 10
            },
            onGoBack = { navController.popBackStack() }
        )
        return
    }

    // Random extra info per question
    val extraInfo = remember(quizState.round) {
        quizState.currentRound?.correct?.let {
            if (Random.nextBoolean())
                "Population: %,d".format(it.population)
            else
                "Capital: ${it.capital}"
        } ?: ""
    }

    // Main UI
    Box(Modifier.fillMaxSize()) {
        // Close button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close Quiz")
        }

        quizState.currentRound?.let { round ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // ── Progress row ──
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Guessed: ${quizState.score}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${answeredCount + 1}/${quizState.totalRounds}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // ── Timer ──
                Text(
                    text = "Time left: $timeLeft",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                // ── Question content ──
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
