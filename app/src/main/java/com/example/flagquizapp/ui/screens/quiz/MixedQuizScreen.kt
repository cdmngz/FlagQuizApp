package com.example.flagquizapp.ui.screens.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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

@Composable
fun MixedQuizScreen(
    countries: List<Country>,
    navController: NavHostController
) {
    val quizState = rememberQuizState(
        countries = countries,
        onQuizFinished = { /* handled via .finished check */ }
    )

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

    LaunchedEffect(quizState.answered) {
        if (quizState.answered && !quizState.finished) {
            quizState.advance()
        }
    }

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

        val current = quizState.currentRound

        if (current != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp)
            ) {
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
