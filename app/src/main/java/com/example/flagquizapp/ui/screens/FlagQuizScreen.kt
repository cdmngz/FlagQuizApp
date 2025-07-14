package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.QuizRound
import com.example.flagquizapp.navigation.Screen
import com.example.flagquizapp.ui.components.FlagQuizContent
import kotlinx.coroutines.delay

@Composable
fun FlagQuizScreen(
    countries: List<Country>,
    navController: NavHostController
) {
    var round by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }
    var clickedCountry by remember { mutableStateOf<Country?>(null) }

    val quizRounds = remember { buildQuizRounds(countries) }
    val totalRounds = quizRounds.size

    if (finished) {
        ScoreScreen(
            score = score,
            total = totalRounds,
            onRestart = {
                round = 0
                score = 0
                finished = false
                answered = false
                clickedCountry = null
            },
            onGoBack = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        return
    }

    val current = quizRounds[round]

    LaunchedEffect(answered) {
        if (answered) {
            delay(1_000)
            if (round < totalRounds - 1) {
                round++
                answered = false
                clickedCountry = null
            } else {
                finished = true
            }
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

        // Main quiz content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
        ) {
            FlagQuizContent(
                correct = current.correct,
                options = current.options,
                answered = answered,
                onOptionClick = { selected ->
                    clickedCountry = selected
                    isCorrect = (selected == current.correct)
                    answered = true
                    if (isCorrect) score++
                }
            )
        }
    }
}

fun buildQuizRounds(allCountries: List<Country>): List<QuizRound> {
    val correctAnswers = allCountries.shuffled()
    val rounds = mutableListOf<QuizRound>()

    for (correct in correctAnswers) {
        val wrongOptions = allCountries
            .filter { it != correct }
            .shuffled()
            .take(3)

        val options = (wrongOptions + correct).shuffled()
        rounds += QuizRound(correct, options)
    }

    return rounds
}
