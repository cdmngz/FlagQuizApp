package com.example.flagquizapp.ui.screens.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.quiz.FlagQuizContent
import com.example.flagquizapp.ui.components.quiz.MapQuizContent
import com.example.flagquizapp.ui.screens.ScoreScreen
import com.example.flagquizapp.ui.screens.quiz.shared.rememberQuizState
import kotlinx.coroutines.delay

@Composable
fun TimedQuizScreen(
    countries: List<Country>,
    navController: NavHostController
) {
    val quizState = rememberQuizState(
        countries = countries,
        onQuizFinished = { /* handled by quizState.finished */ }
    )

    var timeLeft by remember { mutableIntStateOf(10) }

    // Reset timer on new round
    LaunchedEffect(quizState.round) {
        timeLeft = 10
        while (timeLeft > 0 && !quizState.answered) {
            delay(1000)
            timeLeft--
        }

        val current = quizState.currentRound
        if (!quizState.answered && current != null) {
            // Auto-submit a wrong answer
            val wrongCountry = current.options.firstOrNull { it != current.correct }
                ?: Country(
                    name = "Unknown",
                    svgUrl = "",
                    mapUrl = "",
                    continent = current.correct.continent,
                    subregion = current.correct.subregion,
                    population = 0,
                    capital = "",
                    code = ""
                )
            quizState.onAnswerSelected(wrongCountry)
        }
    }

    // Proceed to next round after answer
    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) {
            quizState.advance()
        }
    }

    var startTimeMs by remember { mutableStateOf(System.currentTimeMillis()) }

    if (quizState.finished) {
        val endTimeMs = System.currentTimeMillis()
        ScoreScreen(
            score             = quizState.score,
            total             = quizState.totalRounds,
            durationMs        = endTimeMs - startTimeMs,
            completionTimeMs  = endTimeMs,
            onRestart         = {
                quizState.restart()
                startTimeMs = System.currentTimeMillis()
            },
            onGoBack          = { navController.popBackStack() }
        )
        return
    }

    val current = quizState.currentRound

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Quiz"
            )
        }

        if (current != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp)
            ) {
                Text(
                    text = "Time left: $timeLeft",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )

                val isEvenRound = quizState.round % 2 == 0

                if (isEvenRound) {
                    FlagQuizContent(
                        correct = current.correct,
                        options = current.options,
                        answered = quizState.answered,
                        onOptionClick = { selected ->
                            quizState.onAnswerSelected(selected)
                        }
                    )
                } else {
                    MapQuizContent(
                        correct = current.correct,
                        options = current.options,
                        answered = quizState.answered,
                        onOptionClick = { selected ->
                            quizState.onAnswerSelected(selected)
                        }
                    )
                }
            }
        }
    }
}
