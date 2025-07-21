package com.example.flagquizapp.ui.screens.quiz.shared

import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.QuizRound

fun buildQuizRounds(
    countries: List<Country>,
    totalRounds: Int = 10
): List<QuizRound> {
    val safeTotalRounds = minOf(totalRounds, countries.size)
    val correctAnswers = countries.shuffled().take(safeTotalRounds)
    val rounds = mutableListOf<QuizRound>()

    for (correct in correctAnswers) {
        val wrongOptions = countries
            .filter { it != correct }
            .shuffled()
            .take(3)

        val options = (wrongOptions + correct).shuffled()
        rounds += QuizRound(correct, options)
    }

    return rounds
}

