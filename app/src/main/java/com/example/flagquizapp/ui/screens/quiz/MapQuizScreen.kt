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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.flagquizapp.ui.components.quiz.maps.MapQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapQuizScreen(
    countries: List<Country>,
    navController: NavHostController,
    title: String
) {
    val context = LocalContext.current

    // Load GeoJSON once
    val geoJson = remember {
        runCatching {
            context.assets
                .open("maps/countries.geojson")
                .bufferedReader()
                .use { it.readText() }
        }
            .onFailure { Log.e("MapQuizScreen", "couldn't load geoJson", it) }
            .getOrDefault("{}")
    }

    // Quiz state
    val quizState = rememberQuizState(countries = countries) { }

    // Timing and progress
    var startTimeMs by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var answeredCount by rememberSaveable { mutableIntStateOf(0) }

    // Random extra info when the question changes
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

    // Advance on answer and bump progress
    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) {
            answeredCount++
            quizState.advance()
        }
    }

    // Show score screen when finished
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
            onGoBack = { navController.navigateUp() }
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🗺️ $title") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
        ) {
            // ── Progress row under the AppBar ──
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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

            // ── Centered map content ──
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                quizState.currentRound?.let { round ->
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
