package com.example.flagquizapp.model

data class QuizRound(
    val correct: Country,
    val options: List<Country> // includes correct, shuffled
)
