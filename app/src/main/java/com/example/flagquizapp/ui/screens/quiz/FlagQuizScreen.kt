package com.example.flagquizapp.ui.screens.quiz

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.data.ModeKey
import com.example.flagquizapp.data.ModeProgressStore
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.FlagQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.QuizState
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter

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

    val quizState =
        rememberQuizState(countries = countries, onQuizFinished = {}) // keep callback empty
    var startTimeMs by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        snapshotFlow { quizState.answered }
            .filter { it && !quizState.finished }
            .collectLatest {
                delay(500)
                quizState.advance()
            }
    }

    LaunchedEffect(quizState.finished) {
        if (quizState.finished) saveProgress(context, progressKey, quizState)
    }

    // Existing finished block
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
    ) { padding ->
        val current = quizState.currentRound
        if (current != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                FlagQuizContent(
                    correct = current.correct,
                    options = current.options,
                    answered = quizState.answered,
                    onOptionClick = { quizState.onAnswerSelected(it) }
                )
            }
        }
    }
}