package com.example.flagquizapp.ui.screens.quiz

import android.content.Context
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.data.ModeKey
import com.example.flagquizapp.data.ModeProgressStore
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.flags.FlagQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.QuizState
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlin.random.Random

private suspend fun saveProgress(context: Context, key: ModeKey, state: QuizState) {
    val percent = (state.score * 100f / state.totalRounds).toInt()
    ModeProgressStore.save(context, key, percent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagQuizScreen(
    countries: List<Country>,
    navController: NavHostController,
    title: String,
    modeIndex: Int,
    continentName: String,
    subregionName: String?
) {
    val context = LocalContext.current
    val progressKey = remember { ModeKey(continentName, subregionName, modeIndex) }
    val quizState = rememberQuizState(countries = countries, onQuizFinished = {})

    // Timing & counters
    var startTimeMs by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var answeredCount by rememberSaveable { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable { mutableStateOf<Country?>(null) }

    // Pick extraInfo only when currentRound changes (safely)
    val extraInfo by remember(quizState.currentRound) {
        mutableStateOf(
            quizState.currentRound?.correct?.let {
                if (Random.nextBoolean())
                    "The population is %,d".format(it.population)
                else
                    "The capital is ${it.capital}"
            } ?: ""
        )
    }

    // When they answer: bump count, wait, then advance
    LaunchedEffect(Unit) {
        snapshotFlow { quizState.answered }
            .filter { it && !quizState.finished }
            .collectLatest {
                answeredCount++
                delay(500)
                quizState.advance()
            }
    }

    // Save progress when finished
    LaunchedEffect(quizState.finished) {
        if (quizState.finished) saveProgress(context, progressKey, quizState)
    }

    // Show score screen and bail out
    if (quizState.finished) {
        val endTimeMs = System.currentTimeMillis()
        ScoreScreen(
            score            = quizState.score,
            total            = quizState.totalRounds,
            durationMs       = endTimeMs - startTimeMs,
            completionTimeMs = endTimeMs,
            onRestart        = {
                quizState.restart()
                startTimeMs = System.currentTimeMillis()
                answeredCount = 0
            },
            onGoBack         = { navController.navigateUp() }
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
                            contentDescription = "Go back"
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
            // ── Progress row ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Correct: ${quizState.score}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${answeredCount + 1}/${quizState.totalRounds}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // ── Centered question area ──
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                quizState.currentRound?.let { current ->
                    FlagQuizContent(
                        correct = current.correct,
                        options = current.options,
                        answered = quizState.answered,
                        selected = selectedOption,           // ← pass it down
                        extraInfo = extraInfo,
                        onOptionClick = { country ->
                            selectedOption = country                // ← record the tap
                            quizState.onAnswerSelected(country)
                        }
                    )
                }
            }
        }
    }
}
