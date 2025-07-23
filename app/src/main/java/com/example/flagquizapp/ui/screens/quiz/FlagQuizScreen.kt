package com.example.flagquizapp.ui.screens.quiz

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.FlagQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagQuizScreen(
    countries: List<Country>,
    navController: NavHostController,
    title: String
) {
    val quizState = rememberQuizState(countries = countries, onQuizFinished = {})
    var startTimeMs by remember { mutableLongStateOf(System.currentTimeMillis()) }

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

    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) quizState.advance()
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